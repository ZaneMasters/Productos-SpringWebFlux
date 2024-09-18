package com.reactiveweb.itglobers.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductStatusServiceTest {

    private ProductStatusService productStatusService;

    @BeforeEach
    public void setup() {
        productStatusService = new ProductStatusService();
    }

    @Test
    public void testGetStatusNameActive() {
        String statusName = productStatusService.getStatusName(1);
        assertEquals("Active", statusName);
    }

    @Test
    public void testGetStatusNameInactive() {
        String statusName = productStatusService.getStatusName(0);
        assertEquals("Inactive", statusName);
    }

    @Test
    public void testGetStatusNameUnknown() {
        String statusName = productStatusService.getStatusName(999);
        assertEquals("Unknown", statusName);
    }
}
