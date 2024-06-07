package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.Comment;
import org.example.domain.Great;
import org.example.domain.User.User;
import org.example.domain.User.UserDto;
import org.example.domain.User.UserJoinRequest;
import org.example.repository.CommentRepository;
import org.example.repository.GreatRepository;
import org.example.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor  // 초기화되지 않은 final 필드나, @NotNull 이 붙은 필드에 대해 생성자를 생성
@Service
public class UserService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final GreatRepository greatRepository;
    private final BCryptPasswordEncoder encoder;

    public BindingResult joinValid(UserJoinRequest request, BindingResult bindingResult){
        if (request.getEmail().isEmpty()){
            bindingResult.addError(new FieldError("request", "email", "이메일이 비어있습니다."));
        } else if (userRepository.existsByEmail(request.getEmail())){
            bindingResult.addError(new FieldError("request", "email", "이메일이 중복됩니다."));
        } else if (!request.getEmail().contains("@")){
            bindingResult.addError(new FieldError("request", "email", "이메일 형식에 맞지 않습니다."));
        }

        if (request.getPassword().isEmpty()){
            bindingResult.addError(new FieldError("request", "password", "비밀번호가 비어있습니다."));
        } else if (request.getPassword().length() < 8){
            bindingResult.addError(new FieldError("request", "password", "비밀번호는 8자 이상으로 작성해주세요."));
        } else if (request.getPassword().length() > 15){
            bindingResult.addError(new FieldError("request", "password", "비밀번호는 15자 미만으로 작성해주세요."));
        }

        if (request.getMyName().isEmpty()){
            bindingResult.addError(new FieldError("request", "myName", "이름이 비어있습니다."));
        } else if(request.getMyName().length() > 30){
            bindingResult.addError(new FieldError("request", "myName", "이름은 30자 미만으로 작성해주세요."));
        }

        if (request.getNickname().isEmpty()) {
            bindingResult.addError(new FieldError("request", "nickname", "닉네임이 비어있습니다."));
        } else if (request.getNickname().length() > 10) {
            bindingResult.addError(new FieldError("request", "nickname", "닉네임이 10자가 넘습니다."));
        } else if (request.getNickname().length() < 2) {
            bindingResult.addError(new FieldError("request", "nickname", "닉네임은 2자 이상으로 작성해주세요"));
        } else if (userRepository.existsByNickname(request.getNickname())) {
            bindingResult.addError(new FieldError("request", "nickname", "닉네임이 중복됩니다."));
        }

        if (request.getAge() < 1){
            bindingResult.addError(new FieldError("request", "age", "1세 이상부터 가입가능합니다."));
        } else if(request.getAge() > 110) {
            bindingResult.addError(new FieldError("request", "age", "110세 이하까지 가입가능합니다."));
        }

        String patternPhoneNumber = "^\\d{3}-\\d{3,4}-\\d{4}$";
        if (!Pattern.matches(patternPhoneNumber, request.getPhoneNumber())){
            bindingResult.addError(new FieldError("request", "phoneNumber", "전화번호 형식에 맞지 않습니다."));
        }

        return bindingResult;
    }

    public void join(UserJoinRequest request){
        User joinUser = request.toEntity(encoder.encode(request.getPassword()));
        userRepository.save(joinUser);
    }

    public List<User> users(){
        return userRepository.findAll();
    }


    public User myInfo(String email){
        return userRepository.findByEmail(email).orElseThrow(NullPointerException::new);
    }


    @Transactional
    public Boolean delete(String email, String password){
        User loginUser = userRepository.findByEmail(email).get();

        if(encoder.matches(password, loginUser.getPassword())) {
            List<Great> likes = greatRepository.findAllByUserEmail(email);
            for(Great like:likes){
                like.getBoard().greatChange(like.getBoard().getGreatCount() - 1);
            }

            List<Comment> comments = commentRepository.findAllByUserEmail(email);
            for (Comment comment:comments){
                comment.getBoard().commentChange(comment.getBoard().getCommentCount() - 1);
            }

            userRepository.delete(loginUser);
            return true;
        } else {
            return false;
        }
    }

    public BindingResult editValid(UserDto dto, BindingResult bindingResult, String email){
        User loginUser = userRepository.findByEmail(email).orElseThrow(NullPointerException::new);

        if (dto.getNowPassword().isEmpty()){
            bindingResult.addError(new FieldError("dto", "nowPassword", "현재 비밀번호가 비어있습니다."));
        } else if (!encoder.matches(dto.getNowPassword(), loginUser.getPassword())) {
            bindingResult.addError(new FieldError("dto", "nowPassword", "현재 비밀번호가 틀렸습니다."));
        }

        if (!dto.getNewPassword().equals(dto.getNewPasswordCheck())){
            bindingResult.addError(new FieldError("dto", "newPasswordCheck", "비밀번호가 일치하지 않습니다."));
        }

        if (dto.getNickname().isEmpty()){
            bindingResult.addError(new FieldError("dto", "nickname", "닉네임이 비어있습니다."));
        }
        return bindingResult;
    }

    @Transactional
    public void edit(UserDto userDto, String email){
        User loginUser = userRepository.findByEmail(email).orElseThrow(NullPointerException::new);

        if (userDto.getNewPassword().isEmpty()){
            loginUser.edit(loginUser.getPassword(), userDto);
        } else {
            loginUser.edit(encoder.encode(userDto.getNewPassword()), userDto);
        }
    }
}
