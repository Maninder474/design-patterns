/*
 * Copyright 2023 Open Text.
 *
 * The only warranties for products and services of Open Text and its affiliates and licensors (“Open Text”)
 * are as may be set forth in the express warranty statements accompanying such products and services.
 * Nothing herein should be construed as constituting an additional warranty. Open Text shall not be liable
 * for technical or editorial errors or omissions contained herein. The information contained herein is subject
 * to change without notice.
 * Except as specifically indicated otherwise, this document contains confidential information and a valid
 * license is required for possession, use or copying. If this work is provided to the U.S. Government,
 * consistent with FAR 12.211 and 12.212, Commercial Computer Software, Computer Software
 * Documentation, and Technical Data for Commercial Items are licensed to the U.S. Government under
 * vendor's standard commercial license.
 */

package com.opentext.almsc;

/**
 * <code>TokenGenerator</code> generates a unique token to identify an event condition. 
 * To produce a unique token, hash together the event type, object type, product, and product version 
 * of an event condition. 
 *
 * @author Manny Panis
 */
public class TokenGenerator {
	static public final int POLYNOMIAL = 0xEDB88320;

 public int nCrcTable[];

	public TokenGenerator() {
		nCrcTable = new int[256];
		for (int i = 0; i < nCrcTable.length; i++)
			nCrcTable[i] = 0xFFFFFFFF;
	}

	/**
	 * Generates a 256-word table containing all CRC remainders for every
	 * possible 8-bit byte. The table must be initialized before any CRC
	 * calculations.
	 *
	 * @return None
	 */
	public void initCrcTable() {
		int nCrc;

		for (int i = 0; i < nCrcTable.length; i++) {
			nCrc = i;

			for (int j = 8; j > 0; j--) {
				if ((nCrc & 1) != 0) {
					nCrc = (nCrc >> 1) ^ POLYNOMIAL;
				} else {
					nCrc >>= 1;
				}
			}

			nCrcTable[i] = nCrc;
		}
	}

	/**
	 * Calculates the CRC token for a data buffer in a single call. All of the
	 * data must reside in a single buffer.
	 *
	 * @param String
	 *            buffer
	 *
	 * @return CRC token value.
	 */
	public int calculate(StringBuffer strBuf) {
		int nCrc = init();

		nCrc = update(nCrc, strBuf);
		nCrc = finish(nCrc);

		return nCrc;
	}

	/**
	 * Initializes a CRC token value. The value must be initialized before
	 * calling update or finish.
	 *
	 * @param nCrc -
	 *            CRC token
	 *
	 * @return CRC token intial value.
	 */
	public int init() {
		return 0xFFFFFFFF;
	}

	/**
	 * Updates the CRC token after processing each byte of the caller's buffer.
	 *
	 * @param nCrc -
	 *            CRC token, strBuf - string buffer
	 *
	 * @return CRC token value.
	 */
	public int update(int nCrc, StringBuffer strBuf) {
		if (nCrcTable[0] == 0xFFFFFFFF)
			initCrcTable();

		for (int i = 0; i < strBuf.length(); i++)
			nCrc = (nCrc >> 8) ^ nCrcTable[(nCrc ^ strBuf.charAt(i)) & 0xFF];
		return nCrc;
	}

	/**
	 * Completes the CRC token value after all data buffers have been processed.
	 * This must be called after update is called for the last time.
	 *
	 * @param nCrc -
	 *            CRC token
	 *
	 * @return CRC token finish value.
	 */
	public int finish(int nCrc) {
		return (nCrc ^= 0xFFFFFFFF) & 0x7FFFFFFF;
	}
}
