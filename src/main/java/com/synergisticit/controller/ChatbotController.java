package com.synergisticit.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synergisticit.domain.Hotel;
import com.synergisticit.dto.ChatRequest;
import com.synergisticit.service.HotelService;
import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author GesangZeren
 * @project HotelReservation
 * @date 2/11/2025
 */
@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {

    private final OpenAiService openAiService;
    private final HotelService hotelService;
    private final ObjectMapper objectMapper;

    public ChatbotController(OpenAiService openAiService, HotelService hotelService, ObjectMapper objectMapper) {
        this.openAiService = openAiService;
        this.hotelService = hotelService;
        this.objectMapper = objectMapper;
    }

    public ChatCompletionResult createChatCompletionWithRetry(ChatCompletionRequest request) throws InterruptedException {
        int maxRetries = 1;
        int retryCount = 0;
        long backoffMillis = 5000; // Start with a 3-second delay

        while (true) {
            try {
                return openAiService.createChatCompletion(request);
            } catch (Exception ex) {
                String errorMessage = ex.getMessage();
                if (errorMessage.contains("HTTP 429") && retryCount < maxRetries) {
                    retryCount++;
                    System.out.println("Rate limit hit. Retrying after " + backoffMillis + " ms...");
                    Thread.sleep(backoffMillis);
                    backoffMillis *= 2;
                } else {
                    throw ex;
                }
            }
        }
    }

    private String toTitleCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        String[] words = input.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                sb.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) {
                    sb.append(word.substring(1).toLowerCase());
                }
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }

    @PostMapping
    public ResponseEntity<?> chat(@RequestBody ChatRequest chatRequest) throws Exception {
        String userMessage = chatRequest.getMessage();
        System.out.println("User message: " + userMessage);

        // If the user's message contains "hotel", delegate parsing to OpenAI to extract parameters.
        if (userMessage.toLowerCase().contains("hotel")) {
            // Build an extraction prompt instructing OpenAI to output structured parameters.
            String extractionPrompt = "Extract the following parameters from the hotel search query: city, maxPrice, starRating, amenities. " +
                    "Return the result as a JSON object with keys \"city\", \"maxPrice\", \"starRating\", and \"amenities\". " +
                    "If a parameter is not specified, return null. Query: " + userMessage;

            // Create chat messages with a system instruction and the extraction prompt.
            ChatMessage systemMessage = new ChatMessage("system", "You are an assistant that extracts structured hotel search parameters from a natural language query.");
            ChatMessage userChat = new ChatMessage("user", extractionPrompt);
            List<ChatMessage> messages = List.of(systemMessage, userChat);

            // Build the chat completion request.
            ChatCompletionRequest request = ChatCompletionRequest.builder()
                    .model("gpt-3.5-turbo")
                    .messages(messages)
                    .build();

            // Call OpenAI with retry logic.
            ChatCompletionResult result = createChatCompletionWithRetry(request);
            String jsonResponse = result.getChoices().get(0).getMessage().getContent();
            System.out.println("Extracted JSON: " + jsonResponse);

            // Parse the JSON response.
            Map<String, Object> params = objectMapper.readValue(jsonResponse, new TypeReference<Map<String, Object>>() {});


            String city = null;
            if (params.get("city") != null && !params.get("city").toString().trim().isEmpty()) {
                city = toTitleCase(params.get("city").toString());
            }

            double maxPrice;
            try {
                maxPrice = params.get("maxPrice") != null ? Double.parseDouble(params.get("maxPrice").toString()) : 1000.0;
            } catch (NumberFormatException e) {
                maxPrice = 1000.0;
            }
            int starRating = 0;
            try {
                if (params.get("starRating") != null) {
                    // Get the raw starRating string (e.g., "5 stars")
                    String rawStarRating = params.get("starRating").toString();
                    // Remove all non-digit characters (e.g., " stars") so that "5 stars" becomes "5"
                    String starRatingNumber = rawStarRating.replaceAll("[^0-9]", "");
                    if (!starRatingNumber.isEmpty()) {
                        starRating = Integer.parseInt(starRatingNumber);
                    }
                }
            } catch (NumberFormatException e) {
                starRating = 0;
            }
            Set<Integer> starRatingsSet = null;
            if (starRating > 0) {
                starRatingsSet = new HashSet<>();
                starRatingsSet.add(starRating);
            }
            Set<String> amenitiesSet = new HashSet<>();
            if (params.get("amenities") != null) {
                Object amenityObj = params.get("amenities");
                if (amenityObj instanceof List) {
                    List<?> amenityList = (List<?>) amenityObj;
                    for (Object item : amenityList) {
                        if (item != null) {
                            amenitiesSet.add(item.toString().trim().toUpperCase());
                        }
                    }
                } else {
                    String amenitiesStr = amenityObj.toString();
                    for (String amenity : amenitiesStr.split(",")) {
                        if (!amenity.trim().isEmpty()) {
                            amenitiesSet.add(amenity.trim().toUpperCase());
                        }
                    }
                }
            }

            // Perform the hotel search with the extracted parameters.
            List<Hotel> hotels = hotelService.searchHotels(city, null, null, null, null, starRatingsSet, maxPrice, amenitiesSet);
            return ResponseEntity.ok(Map.of("resultType", "search", "hotels", hotels));
        } else {
            // For non-hotel queries, delegate directly to OpenAI for a conversational response.
            ChatMessage systemMessage = new ChatMessage("system", "You are a helpful assistant that provides information about hotels.");
            ChatMessage userChat = new ChatMessage("user", userMessage);
            List<ChatMessage> messages = List.of(systemMessage, userChat);

            ChatCompletionRequest request = ChatCompletionRequest.builder()
                    .model("gpt-3.5-turbo")
                    .messages(messages)
                    .build();

            ChatCompletionResult result = openAiService.createChatCompletion(request);
            String answer = result.getChoices().get(0).getMessage().getContent();
            return ResponseEntity.ok(Map.of("resultType", "chat", "answer", answer));
        }
    }
}