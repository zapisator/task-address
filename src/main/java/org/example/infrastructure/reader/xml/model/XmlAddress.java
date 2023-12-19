package org.example.infrastructure.reader.xml.model;

import io.github.threetenjaxb.core.LocalDateXmlAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.domen.model.Address;
import org.example.infrastructure.UuidConverter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@XmlRootElement(name = "OBJECT")
@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class XmlAddress implements Serializable {
    @XmlAttribute(name = "ID")
    private int id;
    @XmlAttribute(name = "OBJECTID")
    private int objectId;
    @XmlAttribute(name = "OBJECTGUID")
    @XmlJavaTypeAdapter(UuidConverter.class)
    private UUID objectGuid;
    @XmlAttribute(name = "CHANGEID")
    private int changeId;
    @XmlAttribute(name = "NAME")
    private String name;
    @XmlAttribute(name = "TYPENAME")
    private String typename;
    @XmlAttribute(name = "LEVEL")
    private int level;
    @XmlAttribute(name = "OPERTYPEID")
    private int openTypeId;
    @XmlAttribute(name = "PREVID")
    private int prevId;
    @XmlAttribute(name = "NEXTID")
    private int nextId;
    @XmlAttribute(name = "UPDATEDATE")
    @XmlJavaTypeAdapter(LocalDateXmlAdapter.class)
    private LocalDate updateDate;
    @XmlAttribute(name = "STARTDATE")
    @XmlJavaTypeAdapter(LocalDateXmlAdapter.class)
    private LocalDate startDate;
    @XmlAttribute(name = "ENDDATE")
    @XmlJavaTypeAdapter(LocalDateXmlAdapter.class)
    private LocalDate endDate;
    @XmlAttribute(name = "ISACTUAL")
    private boolean isActual;
    @XmlAttribute(name = "ISACTIVE")
    private boolean isActive;

    public static Address convertXmlToModelAddress(XmlAddress xmlAddress) {
        return new Address(
                xmlAddress.getId(),
                xmlAddress.getObjectId(),
                xmlAddress.getObjectGuid(),
                xmlAddress.getChangeId(),
                xmlAddress.getName(),
                xmlAddress.getTypename(),
                xmlAddress.getLevel(),
                xmlAddress.getOpenTypeId(),
                xmlAddress.getPrevId(),
                xmlAddress.getNextId(),
                xmlAddress.getUpdateDate(),
                xmlAddress.getStartDate(),
                xmlAddress.getEndDate(),
                xmlAddress.isActual(),
                xmlAddress.isActive()
        );
    }

}
