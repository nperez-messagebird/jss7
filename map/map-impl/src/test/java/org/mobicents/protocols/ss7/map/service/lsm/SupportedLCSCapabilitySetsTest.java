/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and/or its affiliates, and individual
 * contributors as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a full listing
 * of individual contributors.
 * 
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU General Public License, v. 2.0.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License,
 * v. 2.0 along with this distribution; if not, write to the Free 
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 */
package org.mobicents.protocols.ss7.map.service.lsm;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import org.mobicents.protocols.asn.AsnInputStream;
import org.mobicents.protocols.asn.AsnOutputStream;
import org.mobicents.protocols.asn.Tag;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * TODO : Self generated trace. Get real ones
 * 
 * @author amit bhayani
 *
 */
public class SupportedLCSCapabilitySetsTest {
	
	private byte[] getEncodedData() {
		return new byte[] { 3, 2, 4, 64 };
	}
	
	
	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@BeforeTest
	public void setUp() {
	}

	@AfterTest
	public void tearDown() {
	}

	@Test(groups = { "functional.decode","service.lsm"})
	public void testDecode() throws Exception {
		byte[] rawData = getEncodedData();
		AsnInputStream asn = new AsnInputStream(rawData);

		int tag = asn.readTag();
		SupportedLCSCapabilitySetsImpl supportedLCSCapabilityTest = new SupportedLCSCapabilitySetsImpl();
		supportedLCSCapabilityTest.decodeAll(asn);

		assertEquals( tag,Tag.STRING_BIT);
		assertEquals( asn.getTagClass(),Tag.CLASS_UNIVERSAL);

		assertEquals( (boolean)supportedLCSCapabilityTest.getLcsCapabilitySet1(),false);
		assertEquals( (boolean)supportedLCSCapabilityTest.getLcsCapabilitySet2(),true);
		assertEquals( (boolean)supportedLCSCapabilityTest.getLcsCapabilitySet3(),false);
		assertEquals( (boolean)supportedLCSCapabilityTest.getLcsCapabilitySet4(),false);
	}
	
	@Test(groups = { "functional.encode","service.lsm"})
	public void testEncode() throws Exception {
		
		SupportedLCSCapabilitySetsImpl supportedLCSCapabilityTest = new SupportedLCSCapabilitySetsImpl(false, true, false, false);
		
		AsnOutputStream asnOS = new AsnOutputStream();
		supportedLCSCapabilityTest.encodeAll(asnOS);
		
		byte[] encodedData = asnOS.toByteArray();
		byte[] rawData = getEncodedData();		
		assertTrue( Arrays.equals(rawData,encodedData));
		
	}
	
	@Test(groups = { "functional.serialize", "service.lsm" })
	public void testSerialization() throws Exception {
		SupportedLCSCapabilitySetsImpl original = new SupportedLCSCapabilitySetsImpl(false, true, false, false);

		// serialize
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(out);
		oos.writeObject(original);
		oos.close();

		// deserialize
		byte[] pickled = out.toByteArray();
		InputStream in = new ByteArrayInputStream(pickled);
		ObjectInputStream ois = new ObjectInputStream(in);
		Object o = ois.readObject();
		SupportedLCSCapabilitySetsImpl copy = (SupportedLCSCapabilitySetsImpl) o;
		
		//test result
		assertEquals(copy.getLcsCapabilitySet1(), original.getLcsCapabilitySet1());
		assertEquals(copy.getLcsCapabilitySet2(), original.getLcsCapabilitySet2());
		assertEquals(copy.getLcsCapabilitySet3(), original.getLcsCapabilitySet3());
		assertEquals(copy.getLcsCapabilitySet4(), original.getLcsCapabilitySet4());
		
	}
}