package org.server.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.commons.entity.Banque;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public final class BanqueManagerTest {

	static private Manager<Banque> _manager;
	
	@BeforeClass
	static public final void beforeClass() {
		_manager = Managers.createBanqueManager();
	}
	
	@AfterClass
	static public void afterClass() {
		_manager = null;
	}
	
	@Test
	public final void testAdd() {
		final String locValue = "testAdd";
		final Banque locBanque = new Banque(-1, locValue, locValue, locValue, new AtomicInteger(0));
		final Banque locNewBanque = _manager.add(locBanque);
		assertEquals(locBanque, locNewBanque);
		assertEquals(locBanque.getAnswer(), locNewBanque.getAnswer());
		assertEquals(locBanque.getConstName(), locNewBanque.getConstName());
		assertEquals(locBanque.getDirectory(), locNewBanque.getDirectory());
		assertEquals(false, locNewBanque.getId().equals(-1));
		assertEquals(locNewBanque.getVersion(), 1);
		final boolean locResultat = _manager.remove(locNewBanque.getConstName());
		assertTrue(locResultat);
	}

	@Test
	public final void testRemoveName() {
		final String locValue = "testRemoveName";
		final Banque locBanque = new Banque(-1, locValue, locValue, locValue, new AtomicInteger(0));
		final Banque locNewBanque = _manager.add(locBanque);
		assertEquals(locBanque, locNewBanque);
		assertEquals(locBanque.getAnswer(), locNewBanque.getAnswer());
		assertEquals(locBanque.getConstName(), locNewBanque.getConstName());
		assertEquals(locBanque.getDirectory(), locNewBanque.getDirectory());
		assertEquals(false, locNewBanque.getId().equals(-1));
		assertEquals(locNewBanque.getVersion(), 1);
		final boolean locResultat = _manager.remove(locNewBanque.getConstName());
		assertTrue(locResultat);
		Banque locResultBanque = _manager.find(locNewBanque.getId().intValue());
		assertEquals(locResultBanque, null);
		locResultBanque = _manager.find(locNewBanque.getConstName());
		assertEquals(locResultBanque, null);
	}

	@Test
	public final void testRemoveId() {
		final String locValue = "testRemoveId";
		final Banque locBanque = new Banque(-1, locValue, locValue, locValue, new AtomicInteger(0));
		final Banque locNewBanque = _manager.add(locBanque);
		assertEquals(locBanque, locNewBanque);
		assertEquals(locBanque.getAnswer(), locNewBanque.getAnswer());
		assertEquals(locBanque.getConstName(), locNewBanque.getConstName());
		assertEquals(locBanque.getDirectory(), locNewBanque.getDirectory());
		assertEquals(false, locNewBanque.getId().equals(-1));
		assertEquals(locNewBanque.getVersion(), 1);
		final boolean locResultat = _manager.remove(locNewBanque.getId().intValue());
		assertTrue(locResultat);
		Banque locResultBanque = _manager.find(locNewBanque.getId().intValue());
		assertEquals(locResultBanque, null);
		locResultBanque = _manager.find(locNewBanque.getConstName());
		assertEquals(locResultBanque, null);
	}
	
	@Test
	public final void testMerge() {
		final String locValue = "testMerge";
		final Banque locBanque = new Banque(-1, locValue, locValue, locValue, new AtomicInteger(0));
		final Banque locNewBanque = _manager.add(locBanque);
		assertEquals(locBanque, locNewBanque);
		assertEquals(locBanque.getAnswer(), locNewBanque.getAnswer());
		assertEquals(locBanque.getConstName(), locNewBanque.getConstName());
		assertEquals(locBanque.getDirectory(), locNewBanque.getDirectory());
		assertEquals(false, locNewBanque.getId().equals(-1));
		assertEquals(locNewBanque.getVersion(), 1);
		final Banque locMergeBanque = new Banque();
		locMergeBanque.setId(locNewBanque.getId());
		locMergeBanque.setAnswer(locValue);
		locMergeBanque.setDirectory(locValue);
		locMergeBanque.setName(locValue);
		locMergeBanque.setVersion(new AtomicInteger(locNewBanque.getVersion()));
		_manager.merge(locMergeBanque);
		assertNotNull(locMergeBanque);
		assertEquals(locMergeBanque.getId(), locNewBanque.getId());
		assertEquals(locBanque.getAnswer(), locMergeBanque.getAnswer());
		assertEquals(locBanque.getConstName(), locMergeBanque.getConstName());
		assertEquals(locBanque.getDirectory(), locMergeBanque.getDirectory());
		assertEquals(locNewBanque.getId(), locMergeBanque.getId());
		assertEquals(locMergeBanque.getVersion(), 2);
		final boolean locDelete = _manager.remove(locMergeBanque.getConstName());
		assertTrue(locDelete);
	}

	@Test
	public final void testFindInt() {
		final String locValue = "testFindInt";
		final Banque locBanque = new Banque(-1, locValue, locValue, locValue, new AtomicInteger(0));
		final Banque locNewBanque = _manager.add(locBanque);
		assertEquals(locBanque, locNewBanque);
		assertEquals(locBanque.getAnswer(), locNewBanque.getAnswer());
		assertEquals(locBanque.getConstName(), locNewBanque.getConstName());
		assertEquals(locBanque.getDirectory(), locNewBanque.getDirectory());
		assertEquals(false, locNewBanque.getId().equals(-1));
		assertEquals(locNewBanque.getVersion(), 1);
		
		final Banque locFindBanque = _manager.find(locNewBanque.getId().intValue());
		assertNotNull(locFindBanque);
		assertEquals(locNewBanque, locFindBanque);
		assertEquals(locFindBanque.getAnswer(), locNewBanque.getAnswer());
		assertEquals(locFindBanque.getConstName(), locNewBanque.getConstName());
		assertEquals(locFindBanque.getDirectory(), locNewBanque.getDirectory());
		final boolean locResultat = _manager.remove(locNewBanque.getConstName());
		assertTrue(locResultat);
	}

	@Test
	public final void testFindString() {
		final String locValue = "testFindName";
		final Banque locBanque = new Banque(-1, locValue, locValue, locValue, new AtomicInteger(0));
		final Banque locNewBanque = _manager.add(locBanque);
		assertEquals(locBanque, locNewBanque);
		assertEquals(locBanque.getAnswer(), locNewBanque.getAnswer());
		assertEquals(locBanque.getConstName(), locNewBanque.getConstName());
		assertEquals(locBanque.getDirectory(), locNewBanque.getDirectory());
		assertEquals(false, locNewBanque.getId().equals(-1));
		assertEquals(locNewBanque.getVersion(), 1);
		final Banque locFindBanque = _manager.find(locNewBanque.getConstName());
		assertNotNull(locFindBanque);
		assertEquals(locNewBanque, locFindBanque);
		assertEquals(locFindBanque.getAnswer(), locNewBanque.getAnswer());
		assertEquals(locFindBanque.getConstName(), locNewBanque.getConstName());
		assertEquals(locFindBanque.getDirectory(), locNewBanque.getDirectory());
		final boolean locResultat = _manager.remove(locNewBanque.getConstName());
		assertTrue(locResultat);
	}

	@Test
	public final void testFindAll() {
		final String locValue = "testFindAll";
		final Banque locBanque = new Banque(-1, locValue, locValue, locValue, new AtomicInteger(0));
		_manager.add(locBanque);
		_manager.add(locBanque);
		final Banque locNewBanque = _manager.add(locBanque);
		final List<Banque> locBanqueList = _manager.findAll();
		assertNotNull(locBanqueList);
		assertEquals(true, locBanqueList.size() >= 3);
		assertEquals(true, locBanqueList.contains(locNewBanque));
		final boolean locIsRemoved = _manager.remove(locValue);
		assertTrue(locIsRemoved);
	}
}
