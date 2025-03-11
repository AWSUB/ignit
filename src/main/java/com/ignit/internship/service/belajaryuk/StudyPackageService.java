package com.ignit.internship.service.belajaryuk;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ignit.internship.dto.belajaryuk.StudyPackageRequest;
import com.ignit.internship.dto.payment.PaymentNotificationRequest;
import com.ignit.internship.dto.payment.TransactionResponse;
import com.ignit.internship.enums.PaymentStatus;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.belajaryuk.StudyPackage;
import com.ignit.internship.model.profile.UserProfile;
import com.ignit.internship.model.utils.Image;
import com.ignit.internship.repository.belajaryuk.StudyPackageRepository;
import com.ignit.internship.repository.profile.ProfileRepository;
import com.ignit.internship.repository.utils.TagRepository;
import com.ignit.internship.service.payment.PaymentService;
import com.ignit.internship.service.utils.ImageService;

import jakarta.transaction.Transactional;

@Service
public class StudyPackageService {

    private final StudyPackageRepository studyPackageRepository;

    private final TagRepository tagRepository;

    private final PaymentService paymentService;

    private final ImageService imageService;

    private final ProfileRepository profileRepository;

    public StudyPackageService(
        final StudyPackageRepository studyPackageRepository, 
        final TagRepository tagRepository,
        final PaymentService paymentService,
        final ImageService imageService,
        final ProfileRepository profileRepository
    ) {
        this.studyPackageRepository = studyPackageRepository;
        this.tagRepository = tagRepository;
        this.paymentService = paymentService;
        this.imageService = imageService;
        this.profileRepository = profileRepository;
    }

    @Transactional
    public StudyPackage createStudyPackage(MultipartFile file, StudyPackageRequest request) throws Exception {
        Image image = imageService.uploadImage(file);

        StudyPackage studyPackage = studyPackageRepository.save(new StudyPackage(
            request.getTitle(),
            request.getSubtitle(),
            image.getId(),
            request.getPrice(),
            tagRepository.findById(request.getTag()).orElseThrow(() -> new IdNotFoundException("Tag not found"))
        ));
        
        return studyPackage;
    }

    public StudyPackage getStudyPackage(Long id) throws IdNotFoundException {
        return studyPackageRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Study Package not found"));
    }

    public TransactionResponse createStudyPackageTransaction(Long profileId, Long packageId) throws Exception {
        if (studyPackageRepository.existsByProfileIdAndPackageId(profileId, packageId)) {
            throw new Exception("Profile already bought study package");
        }
        StudyPackage studyPackage = getStudyPackage(packageId);
        return paymentService.createTransaction(profileId, studyPackage.getId(), studyPackage.getPrice());
    }

    public void processStudyPackagePayment(PaymentNotificationRequest request) throws IllegalArgumentException, IdNotFoundException {
        if (paymentService.verifyPayment(request) != PaymentStatus.SUCCESS) {
            return;
        }
        
        String[] orderId = request.getOrderId().split("-");
        Long packageId = Long.parseLong(orderId[0]);
        Long profileId = Long.parseLong(orderId[1]);

        if (studyPackageRepository.existsByProfileIdAndPackageId(profileId, packageId)) {
            return;
        }

        StudyPackage studyPackage = getStudyPackage(packageId);

        UserProfile profile = profileRepository.findById(profileId).orElseThrow(() -> new IdNotFoundException("Profile not found"));

        studyPackage.addProfile(profile);
        profile.addStudyPackage(studyPackage);
    }
}
