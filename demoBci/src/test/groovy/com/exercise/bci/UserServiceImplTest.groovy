package com.exercise.bci

import com.exercise.bci.dto.EmailDTO
import com.exercise.bci.dto.PasswordDTO
import com.exercise.bci.dto.PhoneDTO
import com.exercise.bci.dto.UserDataDTO
import com.exercise.bci.entity.Phone
import com.exercise.bci.entity.User
import com.exercise.bci.exceptions.InvalidAuthenticationCredentialsException
import com.exercise.bci.exceptions.InvalidDataException
import com.exercise.bci.exceptions.UnexpectedException
import com.exercise.bci.exceptions.UserAlreadyExistsException
import com.exercise.bci.exceptions.UserNotFoundException
import com.exercise.bci.repository.UserRepository
import com.exercise.bci.security.Token
import com.exercise.bci.service.UserServiceImpl
import io.jsonwebtoken.Claims
import spock.lang.Specification

class UserServiceImplTest extends Specification {

    def "should create new user successfully"() {
        given: "a user entity saved in database"
        def userEntity = new User(userDto.getName(), userDto.getEmail().getValue(), userDto.getPassword().getValueEncrypted(),
                new ArrayList<Phone>(), token);
        def userSaved = userEntity
        userSaved.setUserId("a23b5f1")

        and: "a repository mock that simulates database operations"
        def mockRepo = Mock(UserRepository)
        mockRepo.findByEmail(emailOK) >> null
        mockRepo.save(_) >> userSaved

        and: "a mock Jwt token that simulates token generation"
        def mockToken = Mock(Token)
        mockToken.generate(emailOK) >> token

        when: "we ask for new user creation with valid request data"
        def service = new UserServiceImpl(mockRepo, mockToken)
        def userCreated = service.createUser(userDto)

        then: "the user returned matches the user saved data"
        userCreated.id == userSaved.userId
        userCreated.token == userSaved.token

        where:
        emailOK = "mail@gmail.com"
        passwordOK = "a2asfGfdfdf4"
        token = "eyJhbGciOiJIUzI1NiJ9"
        phones = new ArrayList<PhoneDTO>(
                Arrays.asList(new PhoneDTO(24324l, 43, "ARG"),
                        new PhoneDTO(34543l, 666, "BRA"))
        )
        userDto = new UserDataDTO("some name", new EmailDTO(emailOK), new PasswordDTO(passwordOK), phones)

    }

    def "should get exception when creating user with wrong email format"() {
        given: "a user request with invalid email format"
        def userDto = new UserDataDTO()
        userDto.email = new EmailDTO("mail.gmail.com")

        when: "we ask for new user creation"
        def service = new UserServiceImpl()
        service.createUser(userDto)

        then: "we got an error because user's email is invalid"
        thrown(InvalidDataException)
    }

    def "should get exception when creating user with wrong password format"() {
        given: "a user request with invalid password format"
        def userDto = new UserDataDTO()
        userDto.email = new EmailDTO(emailOK)
        userDto.password = new PasswordDTO(wrongPassword)

        when: "we ask for new user creation"
        def service = new UserServiceImpl()
        service.createUser(userDto)

        then: "we got an error because user's password is invalid"
        thrown(InvalidDataException)

        where:
        emailOK = "mail@gmail.com"
        wrongPassword = "1234"
    }

    def "should get exception when creating user with same existing email"() {
        given: "a user existing in the repository"
        def userFound = new User()
        userFound.email = emailOK

        and: "a repository mock that simulates database operations"
        def mockRepo = Mock(UserRepository)
        mockRepo.findByEmail(emailOK) >> userFound

        and: "a user request that matches that stored user"
        def userDto = new UserDataDTO()
        userDto.email = new EmailDTO(emailOK)
        userDto.password = new PasswordDTO(passwordOK)

        when: "we ask for new user creation"
        def service = new UserServiceImpl(mockRepo, Mock(Token))
        service.createUser(userDto)

        then: "we got an error because user already exists in database"
        thrown(UserAlreadyExistsException)

        where:
        emailOK = "mail@gmail.com"
        passwordOK = "a2asfGfdfdf4"
    }

    def "should get exception when token generation fails"() {
        given: "a valid user creation request"
        def userDto = new UserDataDTO()
        userDto.email = new EmailDTO(emailOK)
        userDto.password = new PasswordDTO(passwordOK)

        and: "a repository mock that simulates database operations"
        def mockRepo = Mock(UserRepository)
        mockRepo.findByEmail(emailOK) >> null

        and: "a mock Jwt token that simulates a failure in token generation"
        def mockToken = Mock(Token)
        mockToken.generate(emailOK) >> { throw new UnexpectedException(tokenErrorMsg) }

        when: "we ask for new user creation"
        def service = new UserServiceImpl(mockRepo, mockToken)
        service.createUser(userDto)

        then: "we got an unexpected token generation error"
        def ex = thrown(UnexpectedException)
        ex.getMessage() == tokenErrorMsg

        where:
        emailOK = "mail@gmail.com"
        passwordOK = "a2asfGfdfdf4"
        tokenErrorMsg = "token generation failed"
    }

    def "should get username from jwt token authentication"() {
        given: "a correct authentication header"
        def token = "sfdgfdh"
        def authHeader = "Bearer " + token

        and: "a mock Jwt token that simulates a correct token parsing"
        def claims = Mock(Claims)
        claims.getSubject() >> expectedSubject
        def mockToken = Mock(Token)
        mockToken.getClaimsFromToken(token) >> claims

        when: "we ask for username from token"
        def service = new UserServiceImpl(Mock(UserRepository), mockToken)
        def username = service.getUsernameFrom(authHeader)

        then: "the user's email (username) is returned"
        username == expectedSubject

        where:
        expectedSubject = "mail@gmail.com"
    }

    def "should get error when fails Jwt token parsing"() {
        given: "an authentication header"
        def token = "sfdgfdh"
        def authHeader = "Bearer " + token

        and: "a mock Jwt token that simulates a failure in token parsing"
        def mockToken = Mock(Token)
        mockToken.getClaimsFromToken(token) >> { throw new InvalidAuthenticationCredentialsException(tokenErrorMsg) }

        when: "we ask for username from token"
        def service = new UserServiceImpl(Mock(UserRepository), mockToken)
        service.getUsernameFrom(authHeader)

        then: "an invalid authentication credentials error is thrown"
        thrown(InvalidAuthenticationCredentialsException)

        where:
        expectedSubject = "mail@gmail.com"
        tokenErrorMsg = "expired token"
    }

    def "should get error from invalid authentication header"() {
        given: "an invalid authentication header"
        def authHeader = ""

        when: "we ask for username from token"
        def service = new UserServiceImpl(Mock(UserRepository), Mock(Token))
        service.getUsernameFrom(authHeader)

        then: "an invalid authentication credentials error is thrown"
        thrown(InvalidAuthenticationCredentialsException)
    }

    def "should get user info from authenticated user"() {
        given: "an existing user in database"
        def userFound = new User()
        userFound.email = email
        userFound.password = password

        and: "a repository mock that simulates database operations"
        def mockRepo = Mock(UserRepository)
        mockRepo.findByEmail(email) >> userFound

        when: "we ask for user data from username"
        def service = new UserServiceImpl(mockRepo, Mock(Token))
        def userReturned = service.getUserInfo(email)

        then: "user data returned matches the user stored in database"
        userReturned.userData.email.getValue() == email
        userReturned.userData.password.getValue() == password

        where:
        email = "mail@gmail.com"
        password = "vefsgsg"
    }

    def "should get user not found error when not existing username in database"() {
        given: "a repository mock that simulates not finding a user by username"
        def mockRepo = Mock(UserRepository)
        mockRepo.findByEmail(email) >> null

        when: "we ask for user data from username"
        def service = new UserServiceImpl(mockRepo, Mock(Token))
        service.getUserInfo(email)

        then: "user data returned matches the user stored in database"
        thrown(UserNotFoundException)

        where:
        email = "mail@gmail.com"
    }

    def "should get unexpected error when failing to save user with new token"() {
        given: "an existing user in database"
        def userFound = new User()
        userFound.email = email

        and: "a repository mock that simulates failure when saving user"
        def mockRepo = Mock(UserRepository)
        mockRepo.findByEmail(email) >> userFound
        mockRepo.save(_) >> { throw new RuntimeException()}

        when: "we ask for user data from username"
        def service = new UserServiceImpl(mockRepo, Mock(Token))
        service.getUserInfo(email)

        then: "user data returned matches the user stored in database"
        thrown(RuntimeException)

        where:
        email = "mail@gmail.com"
    }

    def "should get unexpected error when failing to generate new Jwt token"() {
        given: "an existing user in database"
        def userFound = new User()
        userFound.email = email

        and: "a repository mock that simulates user lookup"
        def mockRepo = Mock(UserRepository)
        mockRepo.findByEmail(email) >> userFound

        and: "a mock Jwt token that simulates new token generation failure"
        def mockToken = Mock(Token)
        mockToken.generate(email) >> { throw new UnexpectedException("token generation failure")}

        when: "we ask for user data from username"
        def service = new UserServiceImpl(mockRepo, mockToken)
        service.getUserInfo(email)

        then: "user data returned matches the user stored in database"
        thrown(UnexpectedException)

        where:
        email = "mail@gmail.com"
    }

}
