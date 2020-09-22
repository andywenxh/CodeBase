# CodeBase
code base repository

<jaxb:bindings xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:jaxb="http://java.sun.com/xml/ns/jaxb" version="2.1">

    <jaxb:bindings>
        <jaxb:globalBindings typesafeEnumMaxMembers="2000" />
    </jaxb:bindings>

    <jaxb:bindings schemaLocation="xml-mgmt-ops.xsd">

        <jaxb:bindings node="//xs:element[@name='do-restore']//xs:attribute[@name='deployment-policy']">
          <jaxb:property name="deploymentPolicyAttribute"/>
        </jaxb:bindings>

        <jaxb:bindings node="//xs:element[@name='do-restore']//xs:attribute[@name='deployment-policy-variables']">
          <jaxb:property name="deploymentPolicyVariablesAttribute"/>
        </jaxb:bindings>

        <jaxb:bindings node="//xs:element[@name='do-import']//xs:attribute[@name='deployment-policy']">
          <jaxb:property name="deploymentPolicyAttribute"/>
        </jaxb:bindings>

        <jaxb:bindings node="//xs:element[@name='do-import']//xs:attribute[@name='deployment-policy-variables']">
          <jaxb:property name="deploymentPolicyVariablesAttribute"/>
        </jaxb:bindings>

        <jaxb:bindings node="//xs:element[@name='do-export']//xs:attribute[@name='deployment-policy']">
          <jaxb:property name="deploymentPolicyAttribute"/>
        </jaxb:bindings>

        <jaxb:bindings node="//xs:element[@name='do-backup']//xs:attribute[@name='deployment-policy']">
          <jaxb:property name="deploymentPolicyAttribute"/>
        </jaxb:bindings>

        <jaxb:bindings node="//xs:element[@name='do-deploy-pattern']//xs:attribute[@name='deployment-policy-variables']">
          <jaxb:property name="deploymentPolicyVariablesAttribute"/>
        </jaxb:bindings>


    </jaxb:bindings>

</jaxb:bindings>
