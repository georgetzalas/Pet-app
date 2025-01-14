package gr.hua.dit.petapp.controllers;

import gr.hua.dit.petapp.services.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private AdminService adminService;

    public AdminController(AdminService adminService)
    {
        this.adminService = adminService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/checkEmail")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        boolean exists = adminService.emailExists(email);
        return ResponseEntity.ok(exists);
    }

}
