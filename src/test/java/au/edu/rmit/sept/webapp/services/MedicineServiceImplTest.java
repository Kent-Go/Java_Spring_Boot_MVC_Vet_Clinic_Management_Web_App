package au.edu.rmit.sept.webapp.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import au.edu.rmit.sept.webapp.models.Medicine;
import au.edu.rmit.sept.webapp.repositories.MedicineRepository;

@SpringBootTest
public class MedicineServiceImplTest {

    @Autowired
    private MedicineServiceImpl medicineService;

    @MockBean
    private MedicineRepository medicineRepository;

    // Positive Test: Get all medicines successfully
    @Test
    public void testGetAllMedicines_Success() {
        // Setup mock data
        List<Medicine> mockMedicines = Arrays.asList(
                new Medicine(1, "Medicine A", "10mg", "20.00"),
                new Medicine(2, "Medicine B", "20mg", "25.00")
        );

        when(medicineRepository.findAll()).thenReturn(mockMedicines);

        // Execute service method
        List<Medicine> medicines = (List<Medicine>) medicineService.getAllMedicines();

        // Assert
        assertNotNull(medicines);
        assertEquals(2, medicines.size());
        assertEquals("Medicine A", medicines.get(0).getName());
        assertEquals("Medicine B", medicines.get(1).getName());

        // Verify that the repository method was called once
        verify(medicineRepository, times(1)).findAll();
    }

    // Negative Test: No medicines found
    @Test
    public void testGetAllMedicines_NoMedicinesFound() {
        when(medicineRepository.findAll()).thenReturn(List.of());

        List<Medicine> medicines = (List<Medicine>) medicineService.getAllMedicines();

        // Assert
        assertNotNull(medicines);
        assertEquals(0, medicines.size());

        // Verify that the repository method was called once
        verify(medicineRepository, times(1)).findAll();
    }

    // Positive Test: Get medicine by name successfully
    @Test
    public void testGetMedicineByName_Success() {
        Medicine mockMedicine = new Medicine(1, "Medicine A", "10mg", "20.00");

        when(medicineRepository.findByName("Medicine A")).thenReturn(mockMedicine);

        // Execute service method
        Medicine medicine = medicineService.getMedicineByName("Medicine A");

        // Assert
        assertNotNull(medicine);
        assertEquals("Medicine A", medicine.getName());
        assertEquals("10mg", medicine.getQuantity());

        // Verify that the repository method was called once
        verify(medicineRepository, times(1)).findByName("Medicine A");
    }

    // Negative Test: Medicine by name not found
    @Test
    public void testGetMedicineByName_NotFound() {
        when(medicineRepository.findByName("NonExistentMedicine")).thenReturn(null);

        // Execute service method
        Medicine medicine = medicineService.getMedicineByName("NonExistentMedicine");

        // Assert
        assertNull(medicine);

        // Verify that the repository method was called once
        verify(medicineRepository, times(1)).findByName("NonExistentMedicine");
    }

    // Boundary Test: Get medicine by empty name
    @Test
    public void testGetMedicineByEmptyName() {
        when(medicineRepository.findByName("")).thenReturn(null);

        // Execute service method
        Medicine medicine = medicineService.getMedicineByName("");

        // Assert
        assertNull(medicine);

        // Verify that the repository method was called once
        verify(medicineRepository, times(1)).findByName("");
    }

    // Positive Test: Create new medicine
    @Test
    public void testCreateMedicine_Success() {
        Medicine newMedicine = new Medicine(0, "New Medicine", "15mg", "30.00");

        when(medicineRepository.save(any(Medicine.class))).thenReturn(newMedicine);

        // Execute service method
        Medicine savedMedicine = medicineService.createMedicine(newMedicine);

        // Assert
        assertNotNull(savedMedicine);
        assertEquals("New Medicine", savedMedicine.getName());
        assertEquals("15mg", savedMedicine.getQuantity());

        // Verify that the repository method was called once
        verify(medicineRepository, times(1)).save(newMedicine);
    }

    // Negative Test: Create medicine with missing name
    @Test
    public void testCreateMedicine_MissingName() {
        Medicine incompleteMedicine = new Medicine();
        incompleteMedicine.setQuantity("10");
        incompleteMedicine.setPrice("100");

        // Expect IllegalArgumentException due to missing name
        assertThrows(IllegalArgumentException.class, () -> {
            medicineService.createMedicine(incompleteMedicine);
        }, "Expected exception for missing medicine name");
    }

    // Negative Test: Create medicine with missing quantity
    @Test
    public void testCreateMedicine_MissingQuantity() {
        Medicine incompleteMedicine = new Medicine();
        incompleteMedicine.setName("Paracetamol");
        incompleteMedicine.setPrice("100");

        // Expect IllegalArgumentException due to missing quantity
        assertThrows(IllegalArgumentException.class, () -> {
            medicineService.createMedicine(incompleteMedicine);
        }, "Expected exception for missing medicine quantity");
    }

    // Negative Test: Create medicine with missing price
    @Test
    public void testCreateMedicine_MissingPrice() {
        Medicine incompleteMedicine = new Medicine();
        incompleteMedicine.setName("Paracetamol");
        incompleteMedicine.setQuantity("10");

        // Expect IllegalArgumentException due to missing price
        assertThrows(IllegalArgumentException.class, () -> {
            medicineService.createMedicine(incompleteMedicine);
        }, "Expected exception for missing medicine price");
    }

    // Boundary Test: Create medicine with invalid quantity
    @Test
    public void testCreateMedicine_InvalidQuantity() {
        // Creating a medicine object with an empty quantity
        Medicine invalidMedicine = new Medicine();
        invalidMedicine.setName("Paracetamol");
        invalidMedicine.setQuantity(""); // Invalid quantity
        invalidMedicine.setPrice("100");

        // Expecting IllegalArgumentException due to empty quantity
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            medicineService.createMedicine(invalidMedicine);
        });

        // Asserting that the exception message matches the expected validation error
        assertEquals("Medicine quantity cannot be empty", exception.getMessage());
    }

}
