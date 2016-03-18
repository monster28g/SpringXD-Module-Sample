/*
 * Copyright 2014 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package my.custom.transformer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import my.custom.transformer.handler.ModelHandler;
import my.custom.transformer.handler.StreamWriter;
import my.custom.transformer.model.BaseModel;
import org.apache.commons.collections4.ListUtils;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.transformer.MessageTransformationException;

import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * @author David Turanski
 */
@MessageEndpoint
public class TweetTransformer {

	private ObjectMapper mapper = new ObjectMapper();

    private ModelHandler modelHandler = new ModelHandler();

	private StreamWriter writer = new StreamWriter();


	@Transformer(inputChannel = "input", outputChannel = "output")
	public List<BaseModel> transform(String payload) {
		try {
			List<BaseModel> models = modelHandler.process(mapper.readValue(payload, new TypeReference<Map<String, Object>>() {}));
			ListUtils.partition(models, 1000).stream().forEach(e -> writer.flush(e));
			return models;
		}
		catch (IOException e) {
			throw new MessageTransformationException("Unable to transform tweet: " + e.getMessage(), e);
		}
	}
}
