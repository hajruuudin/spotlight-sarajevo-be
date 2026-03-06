package com.spotlightsarajevo.modules.event.service;

import com.spotlightsarajevo.modules.event.api.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * EventService interface defines methods for managing events.
 * It includes methods to find events with pagination, create, update, delete events,
 * and retrieve event overviews.
 */
public interface EventService {
    /**
     * Retrieves a paginated list of events based on search term, sorting option, and category filters.
     *
     * @param pageRequest the pagination information
     * @param searchTerm the term to search for in event names or descriptions
     * @param sortOption the option to sort the results
     * @param categoryIds the list of category IDs to filter the events
     * @return ResponseEntity containing a Page of EventShorthandModel
     */
    ResponseEntity<Page<EventShorthandModel>> findEventsPaginated(PageRequest pageRequest, String searchTerm, String sortOption, List<Integer> categoryIds);

    /**
     * Creates a new event based on the provided EventCreateModel.
     *
     * @param eventCreateModel the model containing information to create a new event
     * @return ResponseEntity containing the created EventModel
     */
    ResponseEntity<EventModel> create(EventCreateModel eventCreateModel);

    /**
     * Updates an existing event based on the provided EventUpdateModel.
     *
     * @param eventUpdateModel the model containing updated information for the event
     * @return ResponseEntity containing the updated Event
     */
    ResponseEntity<EventModel> update(EventUpdateModel eventUpdateModel, Principal principal);


    /**
     * Updates an existing event organiser based on the provided EventOrganiserUpdateModel
     *
     * @param request The EventOrganiserUpdateModel sent from the frontend
     * @param principal The principal object sent with the request from the frontend. Used for checking authentication
     * @return ResponseEntity containing the updated EventOrganiser
     */
    ResponseEntity<EventOrganiserModel> update(EventOrganiserUpdateModel request, Principal principal);

    /**
     * Deletes an event by its unique identifier.
     *
     * @param id the unique identifier of the event to be deleted
     * @return ResponseEntity containing the deleted EventModel
     */
    ResponseEntity<EventModel> delete(Integer id);

    /**
     * Retrieves an overview of a specific event by its slug.
     *
     * @param eventSlug the unique slug identifier of the event
     * @return ResponseEntity containing the EventOverviewModel
     */
    ResponseEntity<EventOverviewModel> findEventOverview(String eventSlug);

    /**
     * Checks if the user has marked the event as attended.
     *
     * @param eventId the unique identifier of the event
     * @param principal the security principal representing the logged-in user
     * @return ResponseEntity containing Boolean indicating attended status
     */
    ResponseEntity<Boolean> getEventAttendedStatus(Integer eventId, Principal principal);

    /**
     * Retrieves a list of events for a specific date.
     *
     * @param date the date to filter events
     * @return ResponseEntity containing a List of EventShorthandModel
     */
    ResponseEntity<List<EventShorthandModel>> findEventsForDate(LocalDate date);

    /**
     * Checks which dates in a given month have events.
     *
     * @param year The year of the search variable
     * @param month The month indexed by a number (e.g. 1 = January, 2 = February)
     * @return A list of dates for the current month where there is an event per day.
     */
    ResponseEntity<Map<LocalDate, Boolean>> checkEventDatesForMonth(Integer year, Integer month);
}
