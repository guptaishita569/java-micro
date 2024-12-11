package com.hungerhub.test.serviceimpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.hungerhub.dto.FAQDto;
import com.hungerhub.entity.FAQ;
import com.hungerhub.entity.Product;
import com.hungerhub.repository.FAQRepository;
import com.hungerhub.repository.ProductRepository;
import com.hungerhub.services.admin.faq.FAQServiceImpl;

import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

public class FAQServiceImplTest {
    @Mock
    private FAQRepository faqRepository;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private FAQServiceImpl faqService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public  void testpostFAQ(){
        Long prodid=123L;
        FAQDto faqDto = new FAQDto();
        faqDto.setId(123L);
        faqDto.setQuestion("test Question");
        faqDto.setAnswer("test answer");
        faqDto.setProductId(12L);
        Product product = new Product();
        product.setId(faqDto.getId());
        product.setName(faqDto.getAnswer());
        Mockito.when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        FAQ faq = new FAQ();
        faq.setAnswer("test answer");
        faq.setQuestion("test question");
        faq.setProduct(product);
        Mockito.when(faqRepository.save(ArgumentMatchers.any())).thenReturn(faq);
        faqService.postFAQ(prodid,faqDto);
    }
}
