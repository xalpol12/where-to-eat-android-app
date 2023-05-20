//package dev.xalpol12.wheretoeat.network;
//
//import static org.junit.Assert.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//import androidx.lifecycle.MutableLiveData;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//
//import java.util.List;
//
//import dev.xalpol12.wheretoeat.data.ImageResult;
//import dev.xalpol12.wheretoeat.network.dto.ImageRequestDTO;
//import dev.xalpol12.wheretoeat.network.dto.PlaceRequestDTO;
//
//public class APIRepositoryTest {
//
//    @InjectMocks
//    private APIRepository repository;
//
//    @Mock
//    private APIService apiService;
//
//    @Mock
//    private MutableLiveData<List<ImageResult>> imageList;
//    private ImageRequestDTO imageRequest;
//    @Before
//    public void init() {
//        imageRequest = createImageRequest();
//        APIService apiService = mock(APIService.class);
//    }
//
//    private ImageRequestDTO createImageRequest() {
//        String photoReference = "sample_reference";
//        int height = 100;
//        int width = 100;
//        return new ImageRequestDTO(photoReference, height, width);
//    }
//
//
//    @Test
//    public void givenImageRequestDTO_onSuccessfullResponse_createsNewArrayListWithResponse() {
//        //given
//
//        //when
//        when(apiService.getPlaceList(any(PlaceRequestDTO.class))).thenReturn(Call)
//
//        //then
//    }
//}