package org.ow2.chameleon.fuchsia.importer.philipshue.util;

import org.osgi.framework.Filter;
import org.ow2.chameleon.fuchsia.core.FuchsiaUtils;
import org.ow2.chameleon.fuchsia.core.declaration.ImportDeclaration;
import org.ow2.chameleon.fuchsia.core.exceptions.BinderException;
import org.ow2.chameleon.fuchsia.core.exceptions.InvalidFilterException;

import java.util.Map;

import static org.ow2.chameleon.fuchsia.core.declaration.Constants.ID;
import static org.ow2.chameleon.fuchsia.importer.philipshue.util.Constants.*;

public class PhilipsHueImportDeclarationWrapper {

    private static Filter declarationFilter = buildFilter();

    private String id;
    private String name;
    private String type;
    private Object object;
    private String bridgeType;
    private Object bridgeObject;

    private PhilipsHueImportDeclarationWrapper() {

    }

    private static Filter buildFilter() {
        Filter filter;
        String stringFilter = String.format("(&(%s=*)(%s=*)(%s=*)(%s=*)(%s=*)(%s=*))",
                ID,
                DISCOVERY_PHILIPS_DEVICE_NAME, DISCOVERY_PHILIPS_DEVICE_TYPE, DISCOVERY_PHILIPS_DEVICE_OBJECT,
                DISCOVERY_PHILIPS_BRIDGE_TYPE, DISCOVERY_PHILIPS_BRIDGE_OBJECT);
        try {
            filter = FuchsiaUtils.getFilter(stringFilter);
        } catch (InvalidFilterException e) {
            throw new IllegalStateException(e);
        }
        return filter;
    }

    public static PhilipsHueImportDeclarationWrapper create(ImportDeclaration importDeclaration) throws BinderException {
        Map<String, Object> metadata = importDeclaration.getMetadata();

        if (!declarationFilter.matches(metadata)) {
            throw new BinderException("Not enough information in the metadata to be used by the phillips hue importer");
        }
        PhilipsHueImportDeclarationWrapper wrapper = new PhilipsHueImportDeclarationWrapper();

        wrapper.id = (String) metadata.get(ID);
        wrapper.name = (String) metadata.get(DISCOVERY_PHILIPS_DEVICE_NAME);
        wrapper.type = (String) metadata.get(DISCOVERY_PHILIPS_DEVICE_TYPE);
        wrapper.object = metadata.get(DISCOVERY_PHILIPS_DEVICE_OBJECT);
        wrapper.bridgeType = (String) metadata.get(DISCOVERY_PHILIPS_BRIDGE_TYPE);
        wrapper.bridgeObject = metadata.get(DISCOVERY_PHILIPS_BRIDGE_OBJECT);

        return wrapper;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Object getObject() {
        return object;
    }

    public String getBridgeType() {
        return bridgeType;
    }

    public Object getBridgeObject() {
        return bridgeObject;
    }
}