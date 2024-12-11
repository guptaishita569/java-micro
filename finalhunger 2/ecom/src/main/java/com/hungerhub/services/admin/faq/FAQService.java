package com.hungerhub.services.admin.faq;

import com.hungerhub.dto.FAQDto;

public interface FAQService {

    FAQDto postFAQ(Long productId, FAQDto faqDto);
}
