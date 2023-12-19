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
import org.example.domen.model.Hierarchy;

import java.io.Serializable;
import java.time.LocalDate;

@XmlRootElement(name = "ITEM")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class XmlHierarchy implements Serializable {
    @XmlAttribute(name = "ID")
    private int id;
    @XmlAttribute(name = "OBJECTID")
    private int objectId;
    @XmlAttribute(name = "PARENTOBJID")
    private int parentObjId;
    @XmlAttribute(name = "CHANGEID")
    private int changeId;
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
    @XmlAttribute(name = "ISACTIVE")
    private boolean isActive;

    public static Hierarchy convertXmlToModelHierarchy(XmlHierarchy xmlHierarchy) {
        return new Hierarchy(
                xmlHierarchy.getId(),
                xmlHierarchy.getObjectId(),
                xmlHierarchy.getParentObjId(),
                xmlHierarchy.getChangeId(),
                xmlHierarchy.getPrevId(),
                xmlHierarchy.getNextId(),
                xmlHierarchy.getUpdateDate(),
                xmlHierarchy.getStartDate(),
                xmlHierarchy.getEndDate(),
                xmlHierarchy.isActive()
        );
    }
}
