/*
 * This file is part of the DITA Open Toolkit project.
 * See the accompanying license.txt file for applicable licenses.
 */

/*
 * (c) Copyright IBM Corp. 2005, 2006 All Rights Reserved.
 */
package org.dita.dost.util;

import static org.dita.dost.util.Constants.*;

import java.io.File;
import java.util.HashMap;

import org.apache.xml.resolver.Catalog;
import org.apache.xml.resolver.CatalogManager;
import org.apache.xml.resolver.tools.CatalogResolver;
import org.dita.dost.log.DITAOTJavaLogger;

/**
 * General catalog file resolving utilities.
 * @version 1.0 2005-4-11
 * @author Zhang, Yuan Peng
 */

public final class CatalogUtils {
    /**logger to log informations.*/
    private static DITAOTJavaLogger logger = new DITAOTJavaLogger();
    /**apache catalogResolver.*/
    private static CatalogResolver catalogResolver = null;
    /**
     * Instances should NOT be constructed in standard programming.
     */
    private CatalogUtils() {
        // leave blank as designed
    }

    /**
     * Get CatalogResolver.
     * @return CatalogResolver
     */
    public static synchronized CatalogResolver getCatalogResolver() {
        if (catalogResolver == null) {
            final CatalogManager manager = new CatalogManager();
            manager.setIgnoreMissingProperties(true);
            manager.setUseStaticCatalog(false); // We'll use a private catalog.
            manager.setPreferPublic(true);

            //manager.setVerbosity(10);
            catalogResolver = new CatalogResolver(manager);

            final File catalogFilePath = new File(Configuration.pluginResourceDirs.get("org.dita.base"), FILE_NAME_CATALOG);

            final Catalog catalog = catalogResolver.getCatalog();
            try {
                catalog.parseCatalog(catalogFilePath.toURI().toURL());
            } catch (final Exception e) {
                logger.logError(e.getMessage(), e) ;
            }
        }

        return catalogResolver;
    }
}

