/*
 * Copyright (C) 2003-2007 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
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
