package com.google.gwt.sample.stockwatcher.client.data;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;


@Entity
public class World {
	@Id Long id;
	String name = "The Only World";
}
