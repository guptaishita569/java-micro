package com.hungerhub.services.admin.faq;

import com.hungerhub.dto.FAQDto;
import com.hungerhub.entity.FAQ;
import com.hungerhub.entity.Product;
import com.hungerhub.repository.FAQRepository;
import com.hungerhub.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FAQServiceImpl implements FAQService{
	@Autowired
    private  FAQRepository faqRepository;
	@Autowired
    private  ProductRepository productRepository;

    public FAQDto postFAQ(Long productId, FAQDto faqDto){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isPresent()){
            FAQ faq = new FAQ();

            faq.setQuestion(faqDto.getQuestion());
            faq.setAnswer(faqDto.getAnswer());
            faq.setProduct(optionalProduct.get());

            return faqRepository.save(faq).getFAQDto();

        }
        return null;
    }
}
