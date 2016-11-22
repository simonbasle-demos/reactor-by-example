/*
MIT License

Copyright (c) 2016 Victor J Grazi

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package com.vgrazi.play;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Victor J Grazi
 */
//source: https://github.com/vgrazi/rxjava-snippets/
public class PriceTick {
	private final int sequence;
	private final Date date;
	private final String instrument;
	private final double price;
	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("hh:mm:ss");


	public PriceTick(int sequence, Date date, String instrument, double price) {
		this.sequence = sequence;
		this.date = date;
		this.instrument = instrument;
		this.price = price;
	}

	public int getSequence() {
		return sequence;
	}

	public Date getDate() {
		return date;
	}

	public String getInstrument() {
		return instrument;
	}

	public double getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return String.format("%6d %s %s %s", sequence, DATE_FORMAT.format(new Date()), instrument, price);
	}

	public boolean isLast() {
//		return false;
    return sequence >= 10;
	}

}