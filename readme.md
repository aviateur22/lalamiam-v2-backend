## aviateur22/lalamiam-v2-backend
## Techno

JAVA 17

## Build du projet

### Variable d'environment attentdu pour le build du project

#### Base de données
> DATABASE_URL
 
> DATABASE_USER

> DATABASE_PASSWORD

#### JWT
> JWT_SECRET

#### Captcha
>CAPTCHA_SECRET

#### Mail 
>MAILER_ACCOUNT

>MAILER_PASSWORD

#### Crypto

> CRIPTO_KEY

> CRIPTO_SALT
 

### Dependencies

 ### CORE Module

- Dependencies of:
  - Junit-Jupiter: 5.9
  - Mockito: 5.3

###  INFRA module

- Dependencies of:
    - SpringBoot: 3.1
    - SringBootSecurity: 3.1
    - SrpingBootAnnotation: 3.1
    - Spring data jpa: 3.1
    - Hibernate: 6.0
    - PostgreSQL: 42.6
    - Junit-Jupiter: 5.9
    - Mockito: 5.3
    - java-jw: 4.4.0
    - passay: 1.6.4
    - com.auth0

## UseCase in core module

### public U execute(T input) 
Core method that defines the execution of the UseCase. It takes an input of type T and returns an output of type U

### public interface Input{} 
Nested interface that define the structure or type of input expected by the UseCase. 
Specific implementations will extend this
public interface Output{}

### public interface Output{}
Nested interface that define the structure or type of output produced by a UseCase. 
Specific implementations will extend this.

### abstract class InputBase<T, U>
Generic abstract base class for the Input of the UseCase

### abstract class OutputBase<T extends U, U>
Generic abstract base class for the Output of the UseCase

### Example of UseCase implementation
``` java
public interface IBoundariesAdapter {
  /**
   * Entée du useCase
   */
  public interface IBoundaryInputAdapter {
    String getHashPassword();
    String getEmail();
    String getUserName();
  }

  /**
   * Sortie du useCase
   */
  public interface IBoundaryOutputAdapter extends IResponse {
    Long getUserId();

  }
}

public class ClientInscriptionUseCase implements UseCase<ClientInscriptionUseCase.Input, ClientInscriptionUseCase.Output> {

  public ClientInscriptionUseCase() {}


  @Override
  public Output execute(Input input) {
  
    BoundaryInputImpl clientInscriptionInformation = input.getBoundaryInput();    

    .....
    
    BoundaryOutputImpl boundaryOutput = BoundaryOutputImpl.getBoundaryOutputImpl();
    return Output.getUseCaseOutput(boundaryOutput);
  }  
  /**
   * USeCase Input    
   */
  public static class Input extends InputBase<IBoundaryInputAdapter, BoundaryInputImpl> implements UseCase.Input {

    public Input(IBoundaryInputAdapter boundaryInputAdapter) {
      super(boundaryInputAdapter);
    }

    public static Input getUseCaseInput(IBoundaryInputAdapter boundaryInputAdapter) {
      return new Input(boundaryInputAdapter);
    }
    @Override
    protected BoundaryInputImpl getImplementation(IBoundaryInputAdapter inputBoundaryAdapter) {
      return BoundaryInputImpl.getBoundaryInputImpl();
    }
  }

  /**
   * USeCase Output
   */
  public static class Output extends OutputBase<BoundaryOutputImpl, IBoundaryOutputAdapter> implements UseCase.Output {
    public Output(BoundaryOutputImpl outputBoundary) {
      super(outputBoundary);
    }

    public static Output getUseCaseOutput(BoundaryOutputImpl outputBoundary) {
      return new Output(outputBoundary);
    }
  }
```

### Call a UseCase
````java
public void registerClient(RegisterClientDto dto) {
        ...
        
  IBoundaryInputAdapter boundaryInputAdapter = ...
  ClientInscriptionUseCase.Input input = ClientInscriptionUseCase.Input.getUseCaseInput(boundaryInputAdapter);
  ClientInscriptionUseCase.Output output = clientInscriptionUseCase.execute(input);
}
````



