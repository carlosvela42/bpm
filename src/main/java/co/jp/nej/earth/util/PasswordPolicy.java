package co.jp.nej.earth.util;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.MstSystem;
import co.jp.nej.earth.service.MstSystemService;
import org.passay.CharacterCharacteristicsRule;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.MessageResolver;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.PropertiesMessageResolver;
import org.passay.Rule;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Component
public class PasswordPolicy {

    @Autowired
    private MstSystemService mstSystemService;

    public List<String> validate(String password) throws EarthException, FileNotFoundException, IOException {
        List<String> listResult = null;

        List<MstSystem> mstSystems = mstSystemService.getMstSystemBySection("passwordPolicy");

        List<Rule> listRule = ruleDefine(mstSystems);
        MessageResolver mr = messageDefine();

        PasswordValidator validator = new PasswordValidator(mr, listRule);
        RuleResult result = validator.validate(new PasswordData(new String(password)));

        if (!result.isValid()) {
            listResult = new ArrayList<String>();
            for (String msg : validator.getMessages(result)) {
                if (!msg.contains("INSUFFICIENT_CHARACTERISTICS")) {
                    listResult.add(msg);
                    return listResult;
                }
            }
        }
        return listResult;
    }

    public List<Rule> ruleDefine(List<MstSystem> listRuleFromDb) {
        LengthRule ruleLength = new LengthRule();
        CharacterCharacteristicsRule rulesItem = new CharacterCharacteristicsRule();
        WhitespaceRule whitespaceRule = null;
        if (!listRuleFromDb.isEmpty()) {

            for (MstSystem item : listRuleFromDb) {

                if (!EStringUtil.isEmpty(item.getConfigValue())) {
                    if (Constant.RuleDefilePasswordPolicy.PASS_UPPERCASE.equals(item.getVariableName())) {
                        rulesItem.getRules().add(new CharacterRule(EnglishCharacterData.UpperCase,
                                Integer.parseInt(item.getConfigValue())));
                    }

                    if (Constant.RuleDefilePasswordPolicy.PASS_LOWERCASE.equals(item.getVariableName())) {
                        rulesItem.getRules().add(new CharacterRule(EnglishCharacterData.LowerCase,
                                Integer.parseInt(item.getConfigValue())));
                    }

                    if (Constant.RuleDefilePasswordPolicy.PASS_DIGIT.equals(item.getVariableName())) {
                        rulesItem.getRules().add(
                                new CharacterRule(EnglishCharacterData.Digit, Integer.parseInt(item.getConfigValue())));
                    }

                    if (Constant.RuleDefilePasswordPolicy.PASS_SPECIAL.equals(item.getVariableName())) {
                        rulesItem.getRules().add(new CharacterRule(EnglishCharacterData.Special,
                                Integer.parseInt(item.getConfigValue())));
                    }

                    if (Constant.RuleDefilePasswordPolicy.PASS_SHORT.equals(item.getVariableName())) {
                        ruleLength.setMinimumLength(Integer.parseInt(item.getConfigValue()));
                    }

                    if (Constant.RuleDefilePasswordPolicy.PASS_LONG.equals(item.getVariableName())) {
                        ruleLength.setMaximumLength(Integer.parseInt(item.getConfigValue()));
                    }
                }

                if (Constant.RuleDefilePasswordPolicy.PASS_WHITESPACE.equals(item.getVariableName())) {
                    if (Constant.RuleDefilePasswordPolicy.NO_WHITE_SPACE.equals(item.getConfigValue())) {
                        whitespaceRule = new WhitespaceRule();
                    }
                }
            }
            if (rulesItem.getRules() != null && rulesItem.getRules().size() > 0) {
                rulesItem.setNumberOfCharacteristics(rulesItem.getRules().size());
            }
        }

        if (whitespaceRule != null) {
            List<Rule> rules = Arrays.asList(ruleLength, rulesItem, whitespaceRule);
            return rules;
        } else {
            List<Rule> rules = Arrays.asList(ruleLength, rulesItem);
            return rules;
        }
    }

    public MessageResolver messageDefine() throws FileNotFoundException, IOException {
        Properties props = new Properties();
        InputStream input = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("paseyMessage.properties");
        props.load(new InputStreamReader(input, "UTF-8"));
        MessageResolver resolver = new PropertiesMessageResolver(props);

        return resolver;
    }

}
