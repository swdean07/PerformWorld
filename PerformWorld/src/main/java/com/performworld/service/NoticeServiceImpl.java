package com.performworld.service;

import com.performworld.domain.Notice;
import com.performworld.dto.NoticeDTO;
import com.performworld.dto.NoticeRequestDTO;
import com.performworld.dto.NoticeResponseDTO;
import com.performworld.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<NoticeDTO> getAllNotices() {
        // 전체 공지사항 조회
        return noticeRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NoticeDTO getNoticeById(Long id) {
        // 특정 ID로 공지사항 조회
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notice not found with id: " + id)); // 예외 처리
        return convertToDTO(notice);
    }

    @Override
    public NoticeDTO updateNotice(Long id, NoticeDTO noticeDTO) {
        // 기존 공지사항 수정
        Notice existingNotice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notice not found with id: " + id)); // 예외 처리

        existingNotice.setTitle(noticeDTO.getTitle());
        existingNotice.setContent(noticeDTO.getContent());

        Notice updatedNotice = noticeRepository.save(existingNotice);
        return convertToDTO(updatedNotice);
    }

    @Override
    public boolean deleteNotice(Long id) {
        // 공지사항 삭제 (예외를 통해 처리)
        if (!isAdmin()) {
            throw new RuntimeException("Only administrators can delete notices.");
        }

        // 공지사항이 존재하는지 확인 후 삭제
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notice not found with id: " + id)); // 예외 처리
        noticeRepository.delete(notice);
        return false;
    }

    @Override
    public NoticeResponseDTO createNotice(NoticeRequestDTO noticeRequestDTO) {
        // DTO에서 Entity로 변환 후 저장
        Notice notice = Notice.builder()
                .title(noticeRequestDTO.getTitle())
                .content(noticeRequestDTO.getContent())
                .build();

        Notice savedNotice = noticeRepository.save(notice);

        // 저장된 Notice를 ResponseDTO로 변환하여 반환
        return convertToResponseDTO(savedNotice);
    }

    private NoticeResponseDTO convertToResponseDTO(Notice notice) {
        return NoticeResponseDTO.builder()
                .noticeId(notice.getNoticeId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createdAt(notice.getCreatedAt())
                .updatedAt(notice.getUpdatedAt())
                .build();
    }

    // DTO -> Entity 변환
    private NoticeDTO convertToDTO(Notice notice) {
        return modelMapper.map(notice, NoticeDTO.class);
    }

    // Entity -> DTO 변환
    private Notice convertToEntity(NoticeDTO noticeDTO) {
        return modelMapper.map(noticeDTO, Notice.class);
    }

    // 관리자 확인 로직 (Spring Security 사용 권장)
    private boolean isAdmin() {
        // 관리자 확인 로직 (예: Spring Security 활용)
        return true; // 임시 true
    }
}


