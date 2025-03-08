package backend.backendbase.controller;

import backend.backendbase.common.result.Result;
import backend.backendbase.data.api.tenant.CreateTenantRequest;
import backend.backendbase.enums.TenantStatus;
import backend.backendbase.service.TenantService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/tenant")
public class TenantController extends BaseController {

    private final TenantService tenantService;

    @PostMapping(value = {"/v1/tenants"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public Result<Void> createTenant(
            @RequestPart("data") @Valid CreateTenantRequest createTenantRequest,
            @RequestPart("files") @Size(min = 2, max = 2) List<MultipartFile> uploadFiles
    ) {
        tenantService.createTenant(createTenantRequest, uploadFiles);
        return Result.success();
    }

    @PutMapping(value = "/v1/tenants/{tenant_id}/approve")
    public Result<Void> approveTenant(@PathVariable(value = "tenant_id") String tenantId) {
        tenantService.updateTenantStatus(tenantId, TenantStatus.APPROVED);
        return Result.success();
    }

    @PutMapping(value = "/v1/tenants/{tenant_id}/reject")
    public Result<Void> rejectTenant(
            @PathVariable(value = "tenant_id") String tenantId
    ) {
        tenantService.updateTenantStatus(tenantId, TenantStatus.REJECTED);
        return Result.success();
    }

    @PostMapping(value = {"/v1/tenants/payment"})
    public Result<Void> payment() {
        // TODO: handle payment info
        return Result.success();
    }

    @PostMapping(value = {"/v1/tenants/setup/{plan_id}"})
    public Result<Void> setupTenant(@PathVariable String plan_id) {
        tenantService.setupTenant(plan_id);
        return Result.success();
    }
}
