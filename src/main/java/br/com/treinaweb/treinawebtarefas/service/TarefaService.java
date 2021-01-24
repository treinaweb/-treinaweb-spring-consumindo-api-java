package br.com.treinaweb.treinawebtarefas.service;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import br.com.treinaweb.treinawebtarefas.domain.Tarefa;

@Service
public class TarefaService {

    private static String webService = "http://localhost:3002/api/";

    public List<Tarefa> listarTarefas() {
        String url = webService + "tarefas";

        List<Tarefa> tarefas;

        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            CloseableHttpResponse response = client.execute(new HttpGet(url));
            String body = EntityUtils.toString(response.getEntity());

            ObjectMapper mapper = new ObjectMapper();
            tarefas = mapper.readValue(body, new TypeReference<List<Tarefa>>(){});
        } catch (Exception e) {
            System.out.println(e.getMessage());
            tarefas = new ArrayList<Tarefa>();
        }

        return tarefas;
    }

    public Tarefa listarTarefaPorId(String id) {
        String url = webService + "tarefas?id=" + id;

        Tarefa tarefa;

        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            CloseableHttpResponse response = client.execute(new HttpGet(url));
            String body = EntityUtils.toString(response.getEntity());

            ObjectMapper mapper = new ObjectMapper();
            tarefa = mapper.readValue(body, Tarefa.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            tarefa = new Tarefa();
        }

        return tarefa;
    }

    public void removerTarefaPorId(String id) {
        String url = webService + "tarefas?id=" + id;

        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            client.execute(new HttpDelete(url));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void cadastrarTarefa(Tarefa tarefa) {
        String url = webService + "tarefas";

        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(url);

            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(Include.NON_EMPTY);
            String tarefaJson = mapper.writeValueAsString(tarefa);

            httpPost.setEntity(new StringEntity(tarefaJson));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-Type", "application/json");

            client.execute(httpPost);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void editarTarefa(Tarefa tarefa) {
        String url = webService + "tarefas?id=" + tarefa.getId();

        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpPut httpPut = new HttpPut(url);

            ObjectMapper mapper = new ObjectMapper();
            String tarefaJson = mapper.writeValueAsString(tarefa);

            httpPut.setEntity(new StringEntity(tarefaJson));
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json");

            client.execute(httpPut);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
