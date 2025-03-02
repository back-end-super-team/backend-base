package backend.backendbase.controller;

import backend.backendbase.data.api.ApiResponse;
import backend.backendbase.data.api.tenant.CreateTenantRequest;
import backend.backendbase.enums.TenantStatus;
import backend.backendbase.service.TenantService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Tenant APIs", description = "Endpoints for managing tenants")
public class TenantController extends BaseController {

    private final TenantService tenantService;

    @PostMapping(value = {"/v1/tenants"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse createTenant(
            @RequestPart("data") @Valid CreateTenantRequest createTenantRequest,
            @RequestPart("files") @Size(min = 2, max = 2) List<MultipartFile> uploadFiles
    ) {
        tenantService.createTenant(createTenantRequest, uploadFiles);
        return ApiResponse.ok();
    }

    @PutMapping(value = "/v1/tenants/{tenant_id}/approve")
    public ApiResponse approveTenant(@PathVariable(value = "tenant_id") String tenantId) {
        tenantService.updateTenantStatus(tenantId, TenantStatus.APPROVED);
        return ApiResponse.ok();
    }

    @PutMapping(value = "/v1/tenants/{tenant_id}/reject")
    public ApiResponse rejectTenant(
            @PathVariable(value = "tenant_id") String tenantId
    ) {
        tenantService.updateTenantStatus(tenantId, TenantStatus.REJECTED);
        return ApiResponse.ok();
    }

    @PostMapping(value = {"/v1/tenants/payment"})
    public ApiResponse payment() {
        // TODO: handle payment info
        return ApiResponse.ok();
    }

    @PostMapping(value = {"/v1/tenants/setup/{plan_id}"})
    public ApiResponse setupTenant(@PathVariable String plan_id) {
        tenantService.setupTenant(plan_id);
        return ApiResponse.ok();
    }
}
