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
package org.fcrepo.kernel.impl.observer.eventmappings;

import static org.modeshape.jcr.api.JcrConstants.JCR_CONTENT;
import static org.fcrepo.kernel.observer.FedoraEvent.PROPERTY_EVENT_TYPES;
import static org.fcrepo.kernel.observer.FedoraEvent.getNodePath;
import static org.fcrepo.kernel.utils.UncheckedFunction.uncheck;
import static org.slf4j.LoggerFactory.getLogger;
import static java.util.stream.Collectors.groupingBy;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

import javax.jcr.RepositoryException;
import javax.jcr.observation.Event;

import org.fcrepo.kernel.exception.RepositoryRuntimeException;
import org.fcrepo.kernel.observer.FedoraEvent;
import org.fcrepo.kernel.observer.eventmappings.InternalExternalEventMapper;

import org.slf4j.Logger;

/**
 * Maps all JCR {@link Event}s concerning one JCR node to one
 * {@link FedoraEvent}. Adds the types of those JCR events together to calculate
 * the final type of the emitted FedoraEvent. TODO stop aggregating events in
 * the heap and make this a purely lazy algorithm, if possible
 *
 * @author ajs6f
 * @since Feb 27, 2014
 */
public class AllNodeEventsOneEvent implements InternalExternalEventMapper {

    private final static Logger log = getLogger(AllNodeEventsOneEvent.class);

    @Override
    public Stream<FedoraEvent> apply(final Stream<Event> events) {
        final BinaryOperator<FedoraEvent> combine = (ev1, ev2) -> {
            final Set<Integer> newTypes = ev2.getTypes();
            newTypes.forEach(ev1::addType);
            // if the first event is a property-event, add the name of the property with which it concerns itself to
            // the second (accumulating) event as an event property
            if (newTypes.stream().anyMatch(PROPERTY_EVENT_TYPES::contains)) {
                try {
                    final String path = ev2.getOriginalPath();
                    log.debug("Found property-event with path: {}", path);
                    ev1.addProperty(path.substring(path.lastIndexOf("/") + 1));
                } catch (final RepositoryException e) {
                    throw new RepositoryRuntimeException(e);
                }
            }
            return ev1;
        };

        final Map<String, List<Event>> groupedEvents =
                events.collect(groupingBy(uncheck(ev -> getNodePath(ev).replaceAll("/" + JCR_CONTENT, "") + "-" +
                        ev.getUserID())));
        return groupedEvents.entrySet().stream().map(
                e -> e.getValue().stream().map(FedoraEvent::new).reduce(combine)).map(Optional::get);
    }
}
