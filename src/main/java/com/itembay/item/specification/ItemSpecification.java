package com.itembay.item.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.itembay.item.domain.Item;
import com.itembay.item.dto.ItemSearchCondition;

import jakarta.persistence.criteria.Predicate;

public class ItemSpecification {
	
	public static Specification<Item> search(ItemSearchCondition cond){
		return(root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			
			if(StringUtils.hasText(cond.getKeyword())) {
				predicates.add(
					cb.like(root.get("title"), "%"+cond.getKeyword()+"%")
				);
			}
			
			if(cond.getItemType() != null) {
				predicates.add(
					cb.equal(root.get("itemType"), cond.getItemType())
				);
			}
			
			if(StringUtils.hasText(cond.getServer())) {
				predicates.add(
					cb.equal(root.get("server"), cond.getServer())
				);
			}
			
			if(cond.getMinPrice() != null) {
				predicates.add(
					cb.greaterThanOrEqualTo(root.get("price"), cond.getMinPrice())
				);
			}
			
			if(cond.getMaxPrice() != null) {
				predicates.add(
					cb.lessThanOrEqualTo(root.get("price"), cond.getMaxPrice())
				);
			}
			
			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}
}
