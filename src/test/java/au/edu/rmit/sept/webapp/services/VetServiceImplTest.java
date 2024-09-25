// package au.edu.rmit.sept.webapp.services;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// import java.util.Optional;
// import java.util.List;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import au.edu.rmit.sept.webapp.models.Vet;
// import au.edu.rmit.sept.webapp.repositories.VetRepository;

// public class VetServiceImplTest {

//     @Mock
//     private VetRepository vetRepository;

//     @InjectMocks
//     private VetServiceImpl vetService;

//     private Vet mockVet;

//     @BeforeEach
//     public void setUp() {
//         MockitoAnnotations.initMocks(this);
//         mockVet = new Vet("Dr.", "English", "Experienced vet", 1, 1);
//         mockVet.setId(1);
//     }

//     // Positive Test: Get all vets
//     @Test
//     public void testGetAllVets_Success() {
//         when(vetRepository.findAll()).thenReturn(List.of(mockVet));
//         List<Vet> result = (List<Vet>) vetService.getAllVets();

//         assertNotNull(result);
//         assertEquals(1, result.size());
//         assertEquals(mockVet.getId(), result.get(0).getId());
//     }

//     // Positive Test: Get vet by valid vetID
//     @Test
//     public void testGetVetByVetID_ValidID() {
//         when(vetRepository.findById(1)).thenReturn(Optional.of(mockVet));
//         Vet result = vetService.getVetByVetID(1);

//         assertNotNull(result);
//         assertEquals(mockVet.getId(), result.getId());
//     }

//     // Negative Test: Get vet by non-existent vetID
//     @Test
//     public void testGetVetByVetID_NonExistentID() {
//         when(vetRepository.findById(999)).thenReturn(Optional.empty());
//         Vet result = vetService.getVetByVetID(999);

//         assertNull(result);
//     }

//     // Positive Test: Get vet by valid userID
//     @Test
//     public void testGetVetByUserID_ValidUserID() {
//         when(vetRepository.findByUserID(1)).thenReturn(Optional.of(mockVet));
//         Vet result = vetService.getVetByUserID(1);

//         assertNotNull(result);
//         assertEquals(mockVet.getUserID(), result.getUserID());
//     }

//     // Negative Test: Get vet by non-existent userID
//     @Test
//     public void testGetVetByUserID_NonExistentUserID() {
//         when(vetRepository.findByUserID(999)).thenReturn(Optional.empty());
//         Vet result = vetService.getVetByUserID(999);

//         assertNull(result);
//     }

//     // Positive Test: Create a valid vet
//     @Test
//     public void testCreateVet_ValidVet() {
//         when(vetRepository.save(any(Vet.class))).thenReturn(mockVet);

//         Vet newVet = new Vet("Dr.", "English", "Experienced vet", 1, 2);
//         Vet result = vetService.createVet(newVet);

//         assertNotNull(result);
//         assertEquals("Dr.", result.getTitle());
//     }

//     // Negative Test: Create a vet with missing data (e.g., title)
//     @Test
//     public void testCreateVet_InvalidVet_MissingTitle() {
//         Vet invalidVet = new Vet(null, "English", "Experienced vet", 1);

//         IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//             vetService.createVet(invalidVet);
//         });

//         assertEquals("Title is required", exception.getMessage());
//         verify(vetRepository, never()).save(invalidVet);  // Ensure repository save was never called
//     }


//     // Boundary Test: Create a vet with minimal userID (boundary case)
//     @Test
//     public void testCreateVet_MinUserIDBoundary() {
//         Vet boundaryVet = new Vet("Dr.", "English", "Experienced vet", 1);
//         boundaryVet.setUserID(1); // Minimal valid ID

//         when(vetRepository.save(any(Vet.class))).thenReturn(boundaryVet);

//         Vet result = vetService.createVet(boundaryVet);
//         assertNotNull(result);
//         assertEquals(1, result.getUserID());
//     }

//     // Boundary Test: Create a vet with maximal userID (boundary case)
//     @Test
//     public void testCreateVet_MaxUserIDBoundary() {
//         Vet boundaryVet = new Vet("Dr.", "English", "Experienced vet", Integer.MAX_VALUE);
//         boundaryVet.setUserID(Integer.MAX_VALUE); // Max valid ID

//         when(vetRepository.save(any(Vet.class))).thenReturn(boundaryVet);

//         Vet result = vetService.createVet(boundaryVet);
//         assertNotNull(result);
//         assertEquals(Integer.MAX_VALUE, result.getUserID());
//     }
// }
