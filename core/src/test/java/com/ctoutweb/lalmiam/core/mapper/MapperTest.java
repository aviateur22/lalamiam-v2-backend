//package mapper;
//
//import adapter.clientInscription.boundary.IBoundariesAdapter.IBoundaryInputAdapter;
//import entity.clientInscription.impl.boundaries.BoundaryInputImpl;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import provider.IClientInscriptionRepository;
//
//import static common.DataForTest.*;
//
//public class MapperTest {
//  @Mock
//  IClientInscriptionRepository userInscriptionRepository;
//
//  @Test
//  public void should_map_ClientInscriptionInformationAdapter_to_ClientInscriptionInformation() {
//    IBoundaryInputAdapter clientInscriptionAdapter = fakeClientInscriptionAdapter();
//
//    BoundaryInputImpl actualResponse = BoundaryInputImpl.getBoundaryInputImpl(
//            clientInscriptionAdapter.getHashPassword(),
//            clientInscriptionAdapter.getEmail(),
//            clientInscriptionAdapter.getName(),
//            clientInscriptionAdapter.getCaptchaResponseByUser(),
//            clientInscriptionAdapter.getCaptchaHashResponse()
//    );
//
//    BoundaryInputImpl expectedResponse = new BoundaryInputImpl(
//            clientInscriptionAdapter.getHashPassword(),
//            clientInscriptionAdapter.getEmail(),
//            clientInscriptionAdapter.getName(),
//            clientInscriptionAdapter.getCaptchaResponseByUser(),
//            clientInscriptionAdapter.getCaptchaHashResponse()
//    );
//
//    Assertions.assertEquals(expectedResponse.getHashPassword(), actualResponse.getHashPassword());
//    Assertions.assertEquals(expectedResponse.getName(), actualResponse.getName());
//    Assertions.assertEquals(expectedResponse.getEmail(), actualResponse.getEmail());
//  }
//
//}
