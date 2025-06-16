package ms.sync.dynamo;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import jakarta.data.Order;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.CrudRepository;
import software.amazon.awssdk.auth.credentials.SystemPropertyCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

public abstract class DynamoBaseRepository<T, K> implements BasicRepository<T, K>, CrudRepository<T, K> {

	private static final Logger log = Logger.getLogger(DynamoBaseRepository.class);

	@ConfigProperty(name = "dynamodb.database")
	protected String database;

	@ConfigProperty(name = "dynamodb.endpoint")
	protected String endpoint;

	@ConfigProperty(name = "dynamodb.region")
	protected String region;

	@ConfigProperty(name = "dynamodb.profile")
	protected String profile;

	@ConfigProperty(name = "dynamodb.awsaccesskey")
	protected String awsaccesskey;

	@ConfigProperty(name = "dynamodb.secretaccess")
	protected String secretaccess;

//	@SuppressWarnings("rawtypes")
//	private Class typeOfT;

	protected DynamoDbEnhancedClient enhancedClient;

	private final Class<T> clazz;

	protected DynamoBaseRepository(Class<T> clazz) {

		this.clazz = clazz;
	}

	protected DynamoDbEnhancedClient getClient() {

		if (enhancedClient != null)
			return enhancedClient;
		
		
		Properties props = System.getProperties();
		props.setProperty("aws.accessKeyId", awsaccesskey);
		props.setProperty("aws.secretAccessKey", secretaccess);
 
		Region awsRegion = Region.of(region);

		final DynamoDbClient client = DynamoDbClient.builder().credentialsProvider(SystemPropertyCredentialsProvider.create())
				.endpointOverride(URI.create(endpoint)).region(awsRegion).build();

		enhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(client).build();

		return enhancedClient;
	}

	@SuppressWarnings("unchecked")
	protected DynamoDbTable<T> getTable() {

		return getClient().table(database, TableSchema.fromBean(clazz));
	}

	@Override
	public <S extends T> S insert(S entity) {

		try {
			DynamoDbTable<T> table = getTable();
			table.putItem(entity);
		} catch (DynamoDbException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}

		return entity;
	}

	@Override
	public <S extends T> List<S> insertAll(List<S> entities) {

		return saveAll(entities);
	}

	@Override
	public <S extends T> S update(S entity) {

		return save(entity);
	}

	@Override
	public <S extends T> List<S> updateAll(List<S> entities) {

		return saveAll(entities);
	}

	@Override
	public <S extends T> S save(S entity) {

		try {
//			String idFieldName = CoreReflectUtils.getAnnotatedField(entity.getClass(), jakarta.nosql.Id.class)
//					.getName();
//			Object idValue = CoreReflectUtils.getAnnotatedFieldVal(entity, jakarta.nosql.Id.class);
//
//			T returnedItem = getTable().getItem(entity);
//			

			getTable().putItem(entity);

		} catch (Exception e) {
			log.warn(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public <S extends T> List<S> saveAll(List<S> entities) {
		// TODO Auto-generated method stub
		for (S e : entities)
			getTable().putItem(e);

		return entities;
	}

	@Override
	public Optional<T> findById(K id) {

		GetItemEnhancedRequest request = GetItemEnhancedRequest.builder()
				.key(Key.builder().partitionValue(id.toString()).build()).build();

		try {
			// If there is no matching item, GetItem does not return any data.
			T returnedItem = getTable().getItem(request);
			if (returnedItem != null)
				System.out.format("No item found with the key %s!\n", id);
			else {
				return Optional.of(returnedItem);
			}

		} catch (DynamoDbException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}

		return Optional.empty();
	}

	@Override
	public Stream<T> findAll() {

		try {
			DynamoDbTable<T> table = getTable();
			return table.scan().items().stream();

		} catch (DynamoDbException e) {
			log.warn(e.getMessage(), e);
		}
		return null;

	}

	@Override
	public Page<T> findAll(PageRequest pageRequest, Order<T> sortBy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(K id) {

		getTable().deleteItem(Key.builder().partitionValue(id.toString()).build());

	}

	@Override
	public void delete(T entity) {
		// TODO Auto-generated method stub

		getTable().deleteItem(entity);

	}

	@Override
	public void deleteAll(List<? extends T> entities) {
		for (T e : entities)
			getTable().deleteItem(e);

	}

}
