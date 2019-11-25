package template.demo.xmlconfig.entity;

public class XmlBeanWrapper {

    private XmlBean xmlBean;

    public XmlBeanWrapper() {
    }

    public XmlBeanWrapper(XmlBean xmlBean) {
        this.xmlBean = xmlBean;
    }

    public XmlBean getXmlBean() {
        return xmlBean;
    }

    public void setXmlBean(XmlBean xmlBean) {
        this.xmlBean = xmlBean;
    }

    @Override
    public String toString() {
        return String.valueOf(xmlBean);
    }

}
