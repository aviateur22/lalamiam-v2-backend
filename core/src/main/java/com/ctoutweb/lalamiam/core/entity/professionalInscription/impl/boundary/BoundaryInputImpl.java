//package com.ctoutweb.lalamiam.core.entity.professionalInscription.impl.boundary;
//
//import com.ctoutweb.lalamiam.core.adapter.professionalInscription.IBoundariesAdapter.IBoundaryInputAdapter;
//
//import java.util.List;
//public record BoundaryInputImpl(
//        String hashPassword,
//        String email,
//        String nickName,
//        String lastName,
//        String firstName,
//        String phone,
//        List<String> documents
//
//) implements IBoundaryInputAdapter {
//  public static BoundaryInputImpl getBoundaryInputImpl(
//          String hashPassword,
//          String email,
//          String nickName,
//          String lastName,
//          String firstName,
//          String phone,
//          List<String> documents
//  ) {
//    return new BoundaryInputImpl(
//            hashPassword,
//            email,
//            nickName,
//            lastName,
//            firstName,
//            phone,
//            documents
//    );
//  }
//
//  @Override
//  public String getHashPassword() {
//    return hashPassword;
//  }
//
//  @Override
//  public String getEmail() {
//    return email;
//  }
//
//  @Override
//  public String getNickName() {
//    return nickName;
//  }
//
//  @Override
//  public String getLastName() {
//    return lastName;
//  }
//
//  @Override
//  public String getFirstName() {
//    return firstName;
//  }
//
//  @Override
//  public String getPhone() {
//    return phone;
//  }
//
//  @Override
//  public List<String> getDocuments() {
//    return documents;
//  }
//}
