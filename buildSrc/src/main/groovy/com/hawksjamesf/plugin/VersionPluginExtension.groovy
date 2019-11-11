package com.hawksjamesf.plugin

import org.gradle.api.DomainObjectSet
import org.gradle.api.NamedDomainObjectContainer

class VersionPluginExtension {

    String buildTypeMatcher

    String fileFormat

    DomainObjectSet domainObjectSet
    NamedDomainObjectContainer namedDomainObjectContainer
}