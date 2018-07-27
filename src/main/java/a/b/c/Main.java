/*
 * Copyright (c) 2009-2018 Ericsson AB, Sweden. All rights reserved.
 *
 * The Copyright to the computer program(s) herein is the property of Ericsson AB, Sweden.
 * The program(s) may be used  and/or copied with the written permission from Ericsson AB
 * or in accordance with the terms and conditions stipulated in the agreement/contract under
 * which the program(s) have been supplied.
 *
 */
package a.b.c;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.sun.net.httpserver.*;

import net.snowyhollows.mcgregor.Event;
import net.snowyhollows.mcgregor.tag.Component;
import net.snowyhollows.mcgregor.tag.Container;
import net.snowyhollows.mcgregor.tag.GenericTag;

/**
 * @author Filip Dreger
 */
public class Main {
	public static void main(String[] args)
			throws IOException {
		Osoba osoba = new Osoba("Jan", "Kowalski", new ArrayList<>(Arrays.asList(
				new Kontakt("660423770", Kontakt.TypKontaktu.MOBILE),
				new Kontakt("fdreger@kowalski.net", Kontakt.TypKontaktu.EMAIL),
				new Kontakt("http://google.com", Kontakt.TypKontaktu.WWW)
		)));

		WidokOsoby component = new WidokOsobyTemplated();
		component.setModel(osoba);

		HttpServer server = HttpServer.create(new InetSocketAddress(8080), 1);
		server.createContext("/", exchange -> {
			byte[] index_html = Files.readAllLines(new File("index.html").toPath()).stream().collect(Collectors.joining("\n")).getBytes();
			exchange.getResponseHeaders().add("Content-type", "text/html;charset=utf-8");
			exchange.sendResponseHeaders(200, index_html.length);
			exchange.getResponseBody().write(index_html);
			exchange.close();
		});

		server.createContext("/do", exchange -> {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			OutputStreamWriter writer = new OutputStreamWriter(baos);
			String[] path = exchange.getRequestURI().getPath().split("/");

			if (path.length > 3) {
				final Component current = component;
				String eventName = path[2];
				String target = path[3];
				String value = path[4];

				Event e = new Event(target, value);
				if (eventName.equals("click")) {
					component.visit(c -> {
						if (target.equals(c.getKey())) {
							((GenericTag) c).getOnclick().onEvent(e);
						}
					});
				} else if (eventName.equals("change")) {
					component.visit(c -> {
						if (target.equals(c.getKey())) {
							((GenericTag) c).getOnchange().onEvent(e);
						}
					});
				}
			}

			component.render(writer);
			writer.flush();
			exchange.sendResponseHeaders(200, baos.size());
			baos.writeTo(exchange.getResponseBody());

			exchange.close();
		});

		server.setExecutor(null);
		server.start();
	}

	public static void mark(Component c, String key, Consumer<Component> consumer) {
		c.setKey(key);
		if (consumer != null) {
			if (consumer != null) {
				consumer.accept(c);
			}
			if (c instanceof Container) {
				List<Component> children = ((Container) c).getChildren();
				int idx = 0;
				if (children != null) {
					for (Component child : children) {
						mark(child, key + ":" + idx++, consumer);
					}
				}
			}
		}
	}
}
