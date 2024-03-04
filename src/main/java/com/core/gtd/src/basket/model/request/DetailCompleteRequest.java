package com.core.gtd.src.basket.model.request;

import com.core.gtd.src.common.constatnt.IsComplete;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DetailCompleteRequest {

    private IsComplete isComplete;
}
