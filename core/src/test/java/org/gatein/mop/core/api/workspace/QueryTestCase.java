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

import org.gatein.mop.core.api.AbstractPOMTestCase;
import org.gatein.mop.core.api.ModelImpl;
import org.gatein.mop.api.workspace.Workspace;
import org.gatein.mop.api.workspace.ObjectType;
import org.gatein.mop.api.workspace.Site;

import java.util.Iterator;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class QueryTestCase extends AbstractPOMTestCase {

  public void testFoo() {
    ModelImpl model = pomService.getModel();
    Workspace workspace = model.getWorkspace();
    Site site = workspace.addSite(ObjectType.PORTAL_SITE, "foo");
    model.save();
    Iterator<Site> sites = model.findObject(ObjectType.PORTAL_SITE, "jcr:path='" + model.pathOf(site) + "'");
    assertTrue(sites.hasNext());
    assertSame(site, sites.next());
    assertFalse(sites.hasNext());
  }
}
