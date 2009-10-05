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

import org.gatein.mop.core.util.Tools;
import org.gatein.mop.api.workspace.ObjectType;
import org.gatein.mop.api.workspace.Site;
import org.gatein.mop.api.workspace.Page;
import org.gatein.mop.api.workspace.Workspace;
import org.gatein.mop.api.workspace.Navigation;
import org.gatein.mop.api.workspace.ui.UIContainer;
import org.gatein.mop.api.workspace.link.PageLink;
import org.gatein.mop.core.api.AbstractPOMTestCase;
import org.gatein.mop.core.api.ModelImpl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Collections;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class WorkspaceTestCase extends AbstractPOMTestCase {

  public void testGetSite() {
    ModelImpl model = pomService.getModel();
    Workspace workspace = model.getWorkspace();
    Site site = workspace.addSite(ObjectType.GROUP_SITE, "site");
    assertNotNull(site);
    Site s2 = model.findObjectById(ObjectType.SITE, site.getObjectId());
    assertEquals(site, s2);
    assertEquals(workspace, site.getWorkspace());
  }

  public void testGetPortal() {
    ModelImpl model = pomService.getModel();
    Workspace workspace = model.getWorkspace();
    Site portal = workspace.addSite(ObjectType.PORTAL_SITE, "portal");
    assertNotNull(portal);
    Site s2 = model.findObjectById(ObjectType.SITE, portal.getObjectId());
    assertEquals(portal, s2);
  }

  public void testGetSites() {
    Workspace workspace = pomService.getModel().getWorkspace();
    Site s1 = workspace.addSite(ObjectType.GROUP_SITE, "foo");
    assertNotNull(s1);
    Site s2 = workspace.addSite(ObjectType.GROUP_SITE, "bar");
    assertNotNull(s2);
    assertEquals(Tools.set(workspace.getSites(ObjectType.GROUP_SITE)), Tools.set(s1, s2));
  }

  public void testRootPage() {
    Workspace workspace = pomService.getModel().getWorkspace();
    Site portal = workspace.addSite(ObjectType.PORTAL_SITE, "portal");
    Page root = portal.getRootPage();
    assertEquals(portal, root.getSite());
  }

  public void testPageAddChild() {
    Workspace workspace = pomService.getModel().getWorkspace();
    Site portal = workspace.addSite(ObjectType.PORTAL_SITE, "portal");
    Page root = portal.getRootPage();
    assertEquals(portal, root.getSite());
    Page foo = root.addChild("foo");
    assertNotNull(foo);
    assertEquals(root, foo.getParent());
    assertEquals(portal, foo.getSite());
    Collection<? extends Page> children = root.getChildren();
    assertEquals(Tools.set(foo), new HashSet<Page>(children));
  }

  public void testPortalTemplate() {
    Workspace workspace = pomService.getModel().getWorkspace();
    Site portal = workspace.addSite(ObjectType.PORTAL_SITE, "portal");
    Page template = portal.getRootPage().addChild("template");
    portal.getRootNavigation().setTemplate(template);
    assertEquals(template, portal.getRootNavigation().getTemplate());
  }

  public void testPageTemplate() {
    Workspace workspace = pomService.getModel().getWorkspace();
    Site portal = workspace.addSite(ObjectType.PORTAL_SITE, "portal");
    Page template = portal.getRootPage().addChild("template");
    Page foo = portal.getRootPage().addChild("foo");
    foo.setTemplate(template);
    assertEquals(template, foo.getTemplate());
  }

  public void testLoading() {
    Workspace workspace = pomService.getModel().getWorkspace();
    Site portal = workspace.addSite(ObjectType.PORTAL_SITE, "portal");
    String portalId = portal.getObjectId();
    Page root = portal.getRootPage();
    String rootId = root.getObjectId();
    Page foo = root.addChild("foo");
    String fooId= foo.getObjectId();

/*
    // Test loading
    Workspace workspace2 = getWorkspace(workspace);

    //
    foo = workspace2.getPage(fooId);
    assertNotNull(foo);
    root = workspace2.getPage(rootId);
    assertNotNull(root);
    portal = workspace2.getSite(portalId);
    assertEquals(root, foo.getParent());
    assertEquals(portal, foo.getSite());
    assertEquals(portal, root.getSite());
*/
  }

  public void testNavigationHierarchy() {
    Workspace workspace = pomService.getModel().getWorkspace();
    Site portal = workspace.addSite(ObjectType.PORTAL_SITE, "portal");
    Navigation rootNav = portal.getRootNavigation();
    assertNotNull(rootNav);
    assertNotNull(rootNav.getAttributes());
    assertNull(rootNav.getParent());
    assertTrue(rootNav.getChildren().isEmpty());
    Navigation fooNav = rootNav.addChild("foo");
    assertNotNull(fooNav);
    assertNotNull(fooNav.getAttributes());
    assertSame(rootNav, fooNav.getParent());
    assertTrue(fooNav.getChildren().isEmpty());
    assertEquals(Collections.singleton(fooNav), new HashSet<Navigation>(rootNav.getChildren()));
  }

  public void testLink() {
    Workspace workspace = pomService.getModel().getWorkspace();
    Site portal = workspace.addSite(ObjectType.PORTAL_SITE, "portal");
    Navigation rootNav = portal.getRootNavigation();
    assertNull(rootNav.getLink());

    //
    PageLink link = rootNav.linkTo(ObjectType.PAGE_LINK);
    assertNotNull(link);
    assertSame(link, rootNav.getLink());
    Page rootPage = portal.getRootPage();
    link.setPage(rootPage);
    assertEquals(rootPage, link.getPage());
  }

  public void testNavigationClear() {
    Workspace workspace = pomService.getModel().getWorkspace();
    Site portal = workspace.addSite(ObjectType.PORTAL_SITE, "portal");
    Navigation rootNav = portal.getRootNavigation();
    rootNav.addChild("a");
    rootNav.addChild("b");
    assertEquals(2, rootNav.getChildren().size());
    rootNav.getChildren().clear();
    assertEquals(0, rootNav.getChildren().size());
  }

  public void testNavigationGetSite() {
    Workspace workspace = pomService.getModel().getWorkspace();
    Site portal = workspace.addSite(ObjectType.PORTAL_SITE, "portal");
    Navigation rootNav = portal.getRootNavigation();
    Navigation a = rootNav.addChild("a");
    Site site = a.getSite();
    assertSame(portal, site);
  }

  public void testRemoveReferencedTemplate() {
    ModelImpl pom = pomService.getModel();
    Workspace workspace = pom.getWorkspace();
    Site portal = workspace.addSite(ObjectType.PORTAL_SITE, "portal");
    Page root = portal.getRootPage();
    Page template = root.addChild("template");
    portal.getRootNavigation().setTemplate(template);
    pom.save();

    pom = pomService.getModel();
    portal = workspace.getSite(ObjectType.PORTAL_SITE, "portal");
    portal.getRootPage().getChild("template").destroy();
    pom.save();

    //
    pom = pomService.getModel();
    portal = workspace.getSite(ObjectType.PORTAL_SITE, "portal");
    portal.destroy();
    pom.save();

    pom = pomService.getModel();
    workspace.addSite(ObjectType.PORTAL_SITE, "portal");

  }

  public void testComponentOrder() {
    ModelImpl pom = pomService.getModel();
    Workspace workspace = pom.getWorkspace();
    Site portal = workspace.addSite(ObjectType.PORTAL_SITE, "portal2");
    Page root = portal.getRootPage();

    UIContainer container = root.getRootComponent();

    container.add(ObjectType.WINDOW, "2");
    container.add(0, ObjectType.WINDOW, "0");

    assertEquals("0", container.get(0).getName());
    assertEquals("2", container.get(1).getName());
    assertEquals(2, container.size());

    container.add(1, ObjectType.WINDOW, "1");

    assertEquals("0", container.get(0).getName());
    assertEquals("1", container.get(1).getName());
    assertEquals("2", container.get(2).getName());
    assertEquals(3, container.size());

    container.add(0, container.get(2));

    assertEquals("2", container.get(0).getName());
    assertEquals("0", container.get(1).getName());
    assertEquals("1", container.get(2).getName());
    assertEquals(3, container.size());
  }
}