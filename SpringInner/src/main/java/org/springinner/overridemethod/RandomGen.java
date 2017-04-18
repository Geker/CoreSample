package org.springinner.overridemethod;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class RandomGen {

    public String getRandom() {
        return this.hashCode() + ":" + String.valueOf(RandomUtils.nextInt());
    }
}
