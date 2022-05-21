package ro.utcn.amqp.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Email {

    @Data
    public static class HtmlTemplate {
        private String templatePath;
        private Map<String, Object> props;

        public HtmlTemplate(String templatePath, Map<String, Object> props) {
            this.templatePath = templatePath;
            this.props = props;
        }
    }

    private String from;
    private String to;
    private String subject;
    private HtmlTemplate htmlTemplate;

    public String getTemplatePath() {
        return htmlTemplate.getTemplatePath();
    }

    public Map<String, Object> getTemplateProps() {
        return htmlTemplate.getProps();
    }
}
