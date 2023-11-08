package com.sff.ownerserver.domain.owner.service;

import com.sff.ownerserver.domain.owner.dto.*;
import com.sff.ownerserver.domain.owner.entity.Owner;
import com.sff.ownerserver.domain.owner.repository.OwnerRepository;
import com.sff.ownerserver.global.error.type.BaseException;
import com.sff.ownerserver.global.utils.ApiError;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void signUp(SignupRequest signupRequest) {
        validateDuplicateMember(signupRequest);
        Owner owner = signupRequest.toEntity();
        owner.passwordEncode(passwordEncoder);
        ownerRepository.save(owner);
    }

    @Override
    @Transactional
    public void deleteOwner(Long ownerId) {
        if (ownerRepository.existsById(ownerId)) {
            ownerRepository.deleteById(ownerId);
        } else {
            throw new BaseException(new ApiError("존재하지 않는 사용자입니다.", 1201));
        }
    }

    @Override
    public OwnerInfoResponse getOwner(Long ownerId) {
        return OwnerInfoResponse.builder().owner(findOwner(ownerId)).build();
    }

    @Override
    @Transactional
    public void updateMember(Long ownerId, MyInfoRequest myInfoRequest) {
        Owner owner = findOwner(ownerId);
        owner.update(myInfoRequest);
    }

    @Override
    @Transactional
    public void updatePoint(Long ownerId, PointUpdateRequest pointUpdateRequest) {
        Owner owner = findOwner(ownerId);
        if (pointUpdateRequest.isCharge()) {
            owner.addPoints(pointUpdateRequest.getAmount());
        } else {
            owner.deductPoints(pointUpdateRequest.getAmount());
        }
    }

    @Override
    public OwnerFcmTokenResponse getFcmToken(Long ownerId) {
        Owner owner = findOwner(ownerId);
        return new OwnerFcmTokenResponse(owner.getId(), owner.getFcmToken());

    }

    private Owner findOwner(Long ownerId) {
        return ownerRepository.findById(ownerId)
                .orElseThrow(() -> new BaseException(new ApiError("존재하지 않는 사용자입니다", 1201)));
    }

    private void validateDuplicateMember(SignupRequest signupRequest) {
        ownerRepository.findByEmail(signupRequest.getEmail())
                .ifPresent(m -> {
                    throw new BaseException(new ApiError("이미 존재하는 계정입니다.", 1211));
                });
    }

}
