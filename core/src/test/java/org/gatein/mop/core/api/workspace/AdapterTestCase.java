/*
 * Copyright (C) 2010 eXo Platform SAS.
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

import org.gatein.mop.api.workspace.ObjectType;
import org.gatein.mop.api.workspace.Site;
import org.gatein.mop.api.workspace.Workspace;
import org.gatein.mop.core.api.AbstractPOMTestCase;
import org.gatein.mop.core.api.ModelImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class AdapterTestCase extends AbstractPOMTestCase
{

   public void testLifeCycle() throws Exception
   {
      ModelImpl model = pomService.getModel();
      Workspace workspace = model.getWorkspace();
      Site site = workspace.addSite(ObjectType.PORTAL_SITE, "adaptable_foo");

      //
      assertFalse(site.isAdapted(Foo.class));

      //
      Foo foo = site.adapt(Foo.class);
      assertNotNull(foo);
      assertSame(foo, site.adapt(Foo.class));
      assertSame(site, foo.adapteeCreated);
      assertSame(Foo.class, foo.typeCreated);
      assertNull(foo.adapteeRemoved);
      assertNull(foo.typeRemoved);
      assertTrue(site.isAdapted(Foo.class));

      //
      site.removeAdapter(Foo.class);
      assertSame(site, foo.adapteeRemoved);
      assertSame(Foo.class, foo.typeRemoved);
      assertFalse(site.isAdapted(Foo.class));
   }

   public void testMixinLifeCycle() throws Exception
   {
      ModelImpl model = pomService.getModel();
      Workspace workspace = model.getWorkspace();
      Site site = workspace.addSite(ObjectType.PORTAL_SITE, "adaptable_secured");

      //
      assertFalse(site.isAdapted(Secured.class));

      //
      Secured secured = site.adapt(Secured.class);
      assertNotNull(secured);
      assertTrue(site.isAdapted(Secured.class));
      secured.setPermissions(new ArrayList<String>());
      List<String> permissions = secured.getPermissions();
      assertTrue(permissions.isEmpty());
      permissions.add("FOO");
      model.save();

      //
      site.removeAdapter(Secured.class);
      assertFalse(site.isAdapted(Secured.class));
   }

   public void testWorkspaceObjectType() throws Exception
   {
      ModelImpl model = pomService.getModel();
      Workspace workspace = model.getWorkspace();
      Site site = workspace.addSite(ObjectType.PORTAL_SITE, "adaptable_foo");
      assertFalse(site.isAdapted(Foo.class));
      Foo foo = site.adapt(Foo.class);
      assertNotNull(foo);
      assertSame(foo, site.adapt(Foo.class));
      assertSame(site, foo.adapteeCreated);
      assertSame(Foo.class, foo.typeCreated);
      assertTrue(site.isAdapted(Foo.class));
   }

   public void testWorkspaceObjectImplType() throws Exception
   {
      ModelImpl model = pomService.getModel();
      Workspace workspace = model.getWorkspace();
      Site site = workspace.addSite(ObjectType.PORTAL_SITE, "adaptable_bar");
      assertFalse(site.isAdapted(Bar.class));
      Bar bar = site.adapt(Bar.class);
      assertNotNull(bar);
      assertSame(bar, site.adapt(Bar.class));
      assertSame(site, bar.adaptee);
      assertSame(Bar.class, bar.adapterType);
      assertTrue(site.isAdapted(Bar.class));
   }

   public void testSiteType() throws Exception
   {
      ModelImpl model = pomService.getModel();
      Workspace workspace = model.getWorkspace();
      assertNull(workspace.adapt(Juu.class));
      Site site = workspace.addSite(ObjectType.PORTAL_SITE, "adaptable_juu");
      assertFalse(site.isAdapted(Juu.class));
      Juu juu = site.adapt(Juu.class);
      assertNotNull(juu);
      assertSame(juu, site.adapt(Juu.class));
      assertSame(site, juu.adaptee);
      assertSame(Juu.class, juu.adapterType);
      assertTrue(site.isAdapted(Juu.class));
   }
}
