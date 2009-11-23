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

import org.gatein.mop.api.workspace.Navigation;
import org.gatein.mop.api.workspace.ObjectType;
import org.gatein.mop.api.workspace.Site;
import org.gatein.mop.core.api.AbstractPOMTestCase;
import org.gatein.mop.core.api.ModelImpl;

import java.util.List;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class NavigationTestCase extends AbstractPOMTestCase
{
   public void testNavigationOrder()
   {
      ModelImpl model = pomService.getModel();
      Site portal = model.getWorkspace().addSite(ObjectType.PORTAL_SITE, "portal_for_navigation");
      Navigation root = portal.getRootNavigation();
      Navigation n1 = root.addChild("1");
      Navigation n2 = root.addChild("2");
      Navigation n3 = root.addChild("3");
      List<? extends Navigation> ns = root.getChildren();
      assertEquals(3, ns.size());
      assertSame(n1, ns.get(0));
      assertSame(n2, ns.get(1));
      assertSame(n3, ns.get(2));
   }
}
