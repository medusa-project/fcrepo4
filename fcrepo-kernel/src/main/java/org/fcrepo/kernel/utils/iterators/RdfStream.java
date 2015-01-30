/**
 * Copyright 2015 DuraSpace, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.fcrepo.kernel.utils.iterators;

import static com.google.common.collect.Sets.newHashSet;
import static com.hp.hpl.jena.graph.Node.ANY;
import static com.hp.hpl.jena.rdf.model.ModelFactory.createDefaultModel;
import static java.util.Objects.hash;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Spliterator;
import java.util.stream.Stream;

import javax.jcr.Session;

import com.google.common.collect.Iterators;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.rdf.model.Model;

/**
 * A stream of RDF triples along with some useful context.
 *
 * @author ajs6f
 * @since Oct 9, 2013
 */
public class RdfStream extends SpliteratorStream<Triple, RdfStream> {

    private final Map<String, String> namespaces = new HashMap<>();
<<<<<<< HEAD

    protected Iterator<Triple> triples;
=======
>>>>>>> First tentative examples, tests pass but Checkstyle freaks

    protected Session context;

    protected Node topic;

    /**
     * Constructor that begins the stream with proffered triples.
     *
<<<<<<< HEAD
     * @param triples the triples
     * @param <Tr> extends {@link Triple}
     * @param <T> extends {@link Iterable}
=======
     * @param triples
>>>>>>> First tentative examples, tests pass but Checkstyle freaks
     */
    public <Tr extends Triple, T extends Stream<Tr>> RdfStream(final T triples) {
        super(triples);
    }

    /**
     * Constructor that begins the stream with proffered triples.
     *
<<<<<<< HEAD
     * @param triples the triples
     * @param <Tr> extends {@link Triple}
     * @param <T> extends {@link Iterable}
=======
     * @param triples
>>>>>>> First tentative examples, tests pass but Checkstyle freaks
     */
    public RdfStream(final Iterator<? extends Triple> triples) {
        super(triples);
    }

    /**
     * Constructor that begins the stream with proffered triples.
     *
<<<<<<< HEAD
     * @param triples the triples
     * @param <Tr> extends {@link Triple}
     * @param <T> extends {@link Collection}
=======
     * @param triples
>>>>>>> First tentative examples, tests pass but Checkstyle freaks
     */
    public RdfStream(final Spliterator<? extends Triple> triples) {
        super(triples);
    }

    /**
     * Constructor that begins the stream with proffered triples.
     *
<<<<<<< HEAD
     * @param triples the triples
     * @param <T> extends {@link Triple}
=======
     * @param triples
>>>>>>> First tentative examples, tests pass but Checkstyle freaks
     */
    public <Tr extends Triple, T extends Iterable<Tr>> RdfStream(final T triples) {
        this(triples.iterator());
    }

    /**
<<<<<<< HEAD
     * Constructor that begins the stream with proffered statements.
     *
     * @param statements the statements
     * @param <T> extends {@link Statement}
     */
    @SafeVarargs
    public <T extends Statement> RdfStream(final T... statements) {
        this(Iterators.transform(Iterators.forArray(statements),
                statement2triple));
    }

    /**
     * Constructor that begins the stream with proffered triple.
     *
     * @param triple the triple
     * @param <T> extends {@link Triple}
     */
    public <T extends Triple> RdfStream(final T triple) {
        this(Iterators.forArray(new Triple[] { triple }));
    }

    /**
     * Constructor that begins the stream without any triples.
     */
    public RdfStream() {
        this(NONE);
    }

    /**
     * Returns the proffered {@link Triple}s with the context of this RdfStream.
     *
     * @param stream the stream
     * @param <Tr> extends {@link Triple}
     * @param <T> extends {@link Iterator}
     * @return proffered Triples with the context of this RDFStream
     */
    public <Tr extends Triple, T extends Iterator<Tr>> RdfStream withThisContext(final T stream) {
        return new RdfStream(stream).namespaces(namespaces()).topic(topic()).session(session());
=======
     * Constructor that begins the stream with proffered triples.
     *
     * @param triples
     */
    @SafeVarargs
    public <T extends Triple> RdfStream(final T... triples) {
        this(Iterators.forArray(triples));
>>>>>>> First tentative examples, tests pass but Checkstyle freaks
    }

    /**
     * Returns the proffered {@link Triple}s with the context of this RdfStream.
     *
<<<<<<< HEAD
     * @param stream the stream
     * @param <Tr> extends {@link Triple}
     * @param <T> extends {@link Iterator}
     * @return proffered Triples with the context of this RDFStream
     */
    public <Tr extends Triple, T extends Iterable<Tr>> RdfStream withThisContext(final T stream) {
        return new RdfStream(stream).namespaces(namespaces()).topic(topic()).session(session());
    }

    /**
     * @param newTriples Triples to add.
     * @return This object for continued use.
     */
    public RdfStream concat(final Iterator<? extends Triple> newTriples) {
        triples = Iterators.concat(triples, newTriples);
        return this;
    }

    /**
     * @param newTriple Triples to add.
     * @param <T> extends {@link Triple}
     * @return This object for continued use.
     */
    public <T extends Triple> RdfStream concat(final T newTriple) {
        triples = Iterators.concat(triples, singletonIterator(newTriple));
        return this;
    }

    /**
     * @param newTriples Triples to add.
     * @param <T> extends {@link Triple}
     * @return This object for continued use.
     */
    public <T extends Triple> RdfStream concat(@SuppressWarnings("unchecked") final T... newTriples) {
        triples = Iterators.concat(triples, Iterators.forArray(newTriples));
        return this;
=======
     * @param stream
     * @return proffered Triples with the context of this RDFStream
     */
    @Override
    public RdfStream withThisContext(final Spliterator<? extends Triple> stream) {
        return new RdfStream(stream).namespaces(namespaces()).topic(topic()).session(session());
>>>>>>> First tentative examples, tests pass but Checkstyle freaks
    }

    /**
     * @param other stream to add.
     * @return This object for continued use.
     */
<<<<<<< HEAD
    public RdfStream concat(final Collection<? extends Triple> newTriples) {
        triples = Iterators.concat(triples, newTriples.iterator());
        return this;
    }

    /**
     * As {@link Iterators#limit(Iterator, int)} while maintaining context.
     *
     * @param limit the limit
     * @return RDFStream
     */
    public RdfStream limit(final Integer limit) {
        return (limit == -1) ? this : withThisContext(Iterators.limit(this, limit));
    }

    /**
     * As {@link Iterators#advance(Iterator, int)} while maintaining context.
     *
     * @param skipNum the skip number
     * @return RDFStream
     */
    public RdfStream skip(final Integer skipNum) {
        Iterators.advance(this, skipNum);
        return this;
    }

    /**
     * As {@link Iterators#filter(Iterator, Predicate)} while maintaining context.
     *
     * @param predicate the predicate
     * @return RdfStream
     */
    public RdfStream filter(final Predicate<? super Triple> predicate) {
        return withThisContext(Iterators.filter(this, predicate));
    }

    /**
     * As {@link Iterators#transform(Iterator, Function)}.
     *
     * @param f function by which to transform
     * @param <ToType> type to which to transform a {@link Triple}
     * @return Iterator of transformed triples
     */
    public <ToType> Iterator<ToType> transform(final Function<? super Triple, ToType> f) {
        return Iterators.transform(this, f);
=======
    public RdfStream concat(final RdfStream other) {
        namespaces(other.namespaces());
        return concat(other.spliterator());
>>>>>>> First tentative examples, tests pass but Checkstyle freaks
    }

    /**
     * As {@link Iterators#transform(Iterator, Function)}, but preserves context.
     *
     * @param f function by which to transform
     * @return transformed RdfStream
     */
    public RdfStream map(final Function<? super Triple, ? extends Triple> f) {
        return withThisContext(Iterators.transform(this, f));
    }


    /**
     * RdfStream
     *
<<<<<<< HEAD
     * @param prefix the prefix
     * @param uri the uri
=======
     * @param prefix
     * @param uri
>>>>>>> First tentative examples, tests pass but Checkstyle freaks
     * @return This object for continued use.
     */
    public RdfStream namespace(final String prefix, final String uri) {
        namespaces.put(prefix, uri);
        return this;
    }

    /**
     * @param nses
     * @return This object for continued use.
     */
    public RdfStream namespaces(final Map<String, String> nses) {
        namespaces.putAll(nses);
        return this;
    }

    /**
     * @return The {@link Session} in context
     */
    public Session session() {
        return this.context;
    }

    /**
     * Sets the JCR context of this stream
     *
     * @param session The {@link Session} in context
     */
    public RdfStream session(final Session session) {
        this.context = session;
        return this;
    }

    /**
     * @return The {@link Node} topic in context
     */
    public Node topic() {
        return this.topic;
    }

    /**
     * Sets the topic of this stream
     *
     * @param topic The {@link Node} topic in context
     */
    public RdfStream topic(final Node topic) {
        this.topic = topic;
        return this;
    }

    /**
     * WARNING! This method exhausts the RdfStream on which it is called!
<<<<<<< HEAD
=======
     * <p>
     * This is a <a href="java.util.stream.Stream">terminal operation</a>.
>>>>>>> First tentative examples, tests pass but Checkstyle freaks
     *
     * @return A {@link Model} containing the prefix mappings and triples in this stream of RDF
     */
    public Model asModel() {
        final Model model = createDefaultModel();
        model.setNsPrefixes(namespaces());
        forEachOrdered(t -> model.add(model.asStatement(t)));
        return model;
    }

    /**
     * @param model A {@link Model} containing the prefix mappings and triples to be put into this stream of RDF
     * @return RDFStream
     */
    public static RdfStream fromModel(final Model model) {
        final Iterator<Triple> modelTriples = model.getGraph().find(ANY, ANY, ANY);
        return new RdfStream(modelTriples).namespaces(model.getNsPrefixMap());
    }

    /**
     * @return Namespaces in scope for this stream.
     */
    public Map<String, String> namespaces() {
        return namespaces;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof RdfStream)) {
            return false;
        }
        final RdfStream rdfo = (RdfStream) o;
        if (!Objects.equals(rdfo.topic(), topic())) {
            return false;
        }
        if (!Objects.equals(rdfo.namespaces(), namespaces())) {
            return false;
        }
        if (!Objects.equals(rdfo.session(), session())) {
            return false;
        }
        final HashSet<? extends Triple> myTriples = newHashSet(iterator());
        final HashSet<? extends Triple> theirTriples = newHashSet(rdfo.iterator());
        if (!myTriples.equals(theirTriples)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return hash(namespaces(), iterator(), topic(), session());
    }

}
