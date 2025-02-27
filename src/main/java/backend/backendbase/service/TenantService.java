package backend.backendbase.service;

import backend.backendbase.config.tenant.TenantContext;
import backend.backendbase.config.tenant.TenantManager;
import backend.backendbase.data.api.tenant.CreateTenantRequest;
import backend.backendbase.entity.Tenant;
import backend.backendbase.entity.TenantDatabase;
import backend.backendbase.entity.TenantInfo;
import backend.backendbase.entity.TenantPlan;
import backend.backendbase.enums.TenantStatus;
import backend.backendbase.exception.RecordNotFoundException;
import backend.backendbase.repository.TenantDatabaseRepository;
import backend.backendbase.repository.TenantInfoRepository;
import backend.backendbase.repository.TenantPlanRepository;
import backend.backendbase.repository.TenantRepository;
import backend.backendbase.utility.Constant;
import backend.backendbase.utility.Utils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class TenantService extends BaseService {

    @Value("${spring.datasource.url}")
    private String databaseBasePath;

    private final TenantManager tenantManager;

    private final TenantRepository tenantRepository;
    private final TenantDatabaseRepository tenantDatabaseRepository;
    private final TenantInfoRepository tenantInfoRepository;
    private final TenantPlanRepository tenantPlanRepository;

    @PostConstruct
    public void init() {
        List<TenantDatabase> listTenantDatabase = tenantDatabaseRepository.findAll();
        listTenantDatabase.forEach(
                tenantDatabase -> tenantManager.addTenant(
                        tenantDatabase.getTenant().getId(),
                        tenantDatabase.getUrl(),
                        tenantDatabase.getUsername(),
                        tenantDatabase.getPassword()
                )
        );
    }

    @Transactional
    public void createTenant(CreateTenantRequest createTenantRequest, List<MultipartFile> uploadFiles) {
        Tenant tenant = Tenant.builder()
                .name(createTenantRequest.tenantName())
                .status(TenantStatus.WAITING)
                .build();
        tenant = tenantRepository.saveAndFlush(tenant);

        TenantInfo tenantInfo = this.modelMapper.map(createTenantRequest, TenantInfo.class);
        tenantInfo.setTenant(tenant);
        for (MultipartFile uploadFile : uploadFiles) {
            if (uploadFile.getOriginalFilename() != null && "front".equals(uploadFile.getOriginalFilename().split("\\.")[0])) {
                tenantInfo.setIdentificationCardFront(MessageFormat.format("/tenant/{0}/info/{1}", tenant.getId(), uploadFile.getOriginalFilename()));
            }
            else if (uploadFile.getOriginalFilename() != null && "back".equals(uploadFile.getOriginalFilename().split("\\.")[0])) {
                tenantInfo.setIdentificationCardBack(MessageFormat.format("/tenant/{0}/info/{1}", tenant.getId(), uploadFile.getOriginalFilename()));
            }
            else {
                throw new MissingFormatArgumentException("Invalid upload file format");
            }
        }
        tenantInfoRepository.saveAndFlush(tenantInfo);
    }

    @Transactional
    public void updateTenantStatus(String tenantId, TenantStatus status) {
        tenantRepository.updateTenantStatus(tenantId, status);
    }

    @SneakyThrows
    @Transactional
    public void setupTenant(String plan_id) {
        String tenantId = TenantContext.getTenantId();
        if (tenantId != null) {
            tenantPlanRepository.findById(plan_id).orElseThrow(() -> new RecordNotFoundException("Plan not found"));
            Tenant tenant = tenantRepository.findById(tenantId).orElseThrow(() -> new RecordNotFoundException("Tenant not found"));
            tenant.setTenantPlanId(plan_id);
            tenantRepository.saveAndFlush(tenant);

            Pattern pattern = Pattern.compile(Constant.DATABASE_PATTERN);
            Matcher matcher = pattern.matcher(databaseBasePath);
            if (!matcher.find()) throw new Exception("Cannot create tenant base path");

            String username = Utils.toCamelCase(tenant.getName());
            String url = matcher.group(1) + username + matcher.group(3);
            String password = Utils.generateRandomPassword(10);
            TenantDatabase tenantDatabase = TenantDatabase.builder()
                    .tenant(tenant)
                    .url(url)
                    .username(username)
                    .password(password)
                    .build();
            tenantDatabase = tenantDatabaseRepository.saveAndFlush(tenantDatabase);
            // TODO: send password, tenant info event to other services

            tenantManager.addTenant(
                    tenantDatabase.getTenant().getId(),
                    tenantDatabase.getUrl(),
                    tenantDatabase.getUsername(),
                    tenantDatabase.getPassword());
        }
    }

}
