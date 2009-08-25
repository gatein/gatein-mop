/**
 * Copyright (C) 2009 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.gatein.mop.core.api.workspace;

import org.gatein.mop.core.api.ModelImpl;
import org.gatein.mop.core.api.AbstractPOMTestCase;
import org.gatein.mop.api.workspace.ObjectType;
import org.gatein.mop.api.workspace.Site;
import org.gatein.mop.api.workspace.Page;
import org.gatein.mop.api.Attributes;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class AttributesTestCase extends AbstractPOMTestCase {

  public void testAttributes() {
    ModelImpl model = pomService.getModel();
    Site portal = model.getWorkspace().addSite(ObjectType.PORTAL_SITE, "portal");
    Attributes portalAttributes = portal.getAttributes();
    portalAttributes.setString("foo", "bar");
    assertEquals("bar", portalAttributes.getString("foo"));
    Attributes pageAttributes = portal.getRootPage().getAttributes();
    pageAttributes.setString("foo", "bar");
    assertEquals("bar", pageAttributes.getString("foo"));
  }

  public void testCascadedAttributes() {
    ModelImpl model = pomService.getModel();
    Site portal = model.getWorkspace().addSite(ObjectType.PORTAL_SITE, "portal");
    Page root = portal.getRootPage();
    Page a = root.addChild("a");
    Attributes rootAttrs = root.getAttributes();
    Attributes aAttrs = a.getAttributes();
    rootAttrs.setString("foo", "foo_root");
    aAttrs.setString("bar", "bar_a");
    rootAttrs.setString("juu", "juu_root");
    aAttrs.setString("juu", "juu_a");
    Attributes combinedAttrs = a.getCascadingAttributes();
    assertEquals("foo_root", combinedAttrs.getString("foo"));
    assertEquals("bar_a", combinedAttrs.getString("bar"));
    assertEquals("juu_a", combinedAttrs.getString("juu"));
  }
}
