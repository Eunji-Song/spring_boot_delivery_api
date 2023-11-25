package song.deliveryapi.common.validation;


import jakarta.validation.GroupSequence;
import song.deliveryapi.common.validation.ValidationGroups.NotEmptyGroup;
import song.deliveryapi.common.validation.ValidationGroups.NotNullGroup;
import song.deliveryapi.common.validation.ValidationGroups.PatternGroup;


/**
 * @Validated 데이터 검증 순서
 */
@GroupSequence({NotEmptyGroup.class, NotNullGroup.class, PatternGroup.class})
public interface ValidationSequence {
}
