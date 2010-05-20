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

import org.gatein.mop.api.workspace.Site;
import org.gatein.mop.api.workspace.Page;
import org.gatein.mop.api.workspace.ObjectType;
import org.gatein.mop.api.workspace.Workspace;
import org.gatein.mop.api.workspace.Navigation;
import org.gatein.mop.api.workspace.ui.UIContainer;
import org.gatein.mop.core.api.ModelImpl;
import org.gatein.mop.core.api.AbstractPOMTestCase;

import java.util.Collection;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class POMTestCase extends AbstractPOMTestCase
{

   public void testPortals() throws Exception
   {
      test(0);
   }

   public void testGroups() throws Exception
   {
      test(1);
   }

   private void test(int type) throws Exception
   {

      ModelImpl model = pomService.getModel();

      Workspace workspace = model.getWorkspace();

      //
      ObjectType<? extends Site> siteType;
      if (type == 0)
      {
         siteType = ObjectType.PORTAL_SITE;
      }
      else if (type == 1)
      {
         siteType = ObjectType.GROUP_SITE;
      }
      else
      {
         throw new UnsupportedOperationException();
      }

      //
      Site site = workspace.addSite(siteType, "default");
      Page root = site.getRootPage();
      assertNotNull(root);
      Page template = root.addChild("template");
      assertNotNull(template);

      UIContainer container = template.getRootComponent();
      assertNotNull(container);
      container.add(ObjectType.WINDOW, "window");

      //
      Page page = root.addChild("page");
      assertNotNull(page);

      //
      NavigationImpl nav = (NavigationImpl)site.getRootNavigation();
      assertNotNull(nav);
//    assertNull(nav.getLink());

      //
      template.templatize(nav);

      //
      PageLinkImpl pageTarget = nav.createPageLink();
//    nav.setLink(pageTarget);
//    pageTarget.setPage(page);

      //
      NavigationImpl subnav = nav.addChild("subnav");
      URLLinkImpl urlTarget = nav.createURLTarget();
//    subnav.setLink(urlTarget);
//    urlTarget.setURL("http://www.exoplatform.com");

      // Try something with template relationships

/*
      Collection<? extends Navigation> templatizedNavigations = template.getTemplatizedObjects(ObjectType.NAVIGATION);
      assertNotNull(templatizedNavigations);
      assertEquals(1, templatizedNavigations.size());
*/

/*
    Collection<Templatized> templatizedObjects = template.getTemplatizedObjects(Templatized.class);
    assertNotNull(templatizedObjects);
    assertEquals(2, templatizedObjects.size());
*/
   }
}
