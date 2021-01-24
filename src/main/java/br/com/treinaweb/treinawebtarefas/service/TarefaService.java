package br.com.treinaweb.treinawebtarefas.service;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.treinaweb.treinawebtarefas.domain.Tarefa;
import br.com.treinaweb.treinawebtarefas.repository.TarefaRepository;

@Service
public class TarefaService {

    private static String webService = "http://localhost:3002/api/";
    
    @Autowired
    private TarefaRepository repository;

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
        repository.deleteById(id);
    }

    public void cadastrarTarefa(Tarefa tarefa) {
        repository.save(tarefa);
    }

    public void editarTarefa(Tarefa tarefa) {
        repository.save(tarefa);
    }
}
