package es.uvigo.esei.daa.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.uvigo.esei.daa.dao.DAOException;
import es.uvigo.esei.daa.dao.UsuariosDAO;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
public class UsuariosResource {
	private final static Logger LOG = Logger.getLogger("UsuariosResource");
	
	private final UsuariosDAO dao;
	
	public UsuariosResource() {
		this.dao = new UsuariosDAO();
	}

	@GET
	public Response list() {
		try {
			return Response.ok(this.dao.list(), MediaType.APPLICATION_JSON).build();
		} catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error listando usuarios", e);
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/{id}")
	public Response get(
		@PathParam("id") int id
	) {
		try {
			return Response.ok(this.dao.get(id), MediaType.APPLICATION_JSON).build();
		} catch (IllegalArgumentException iae) {
			iae.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST)
				.entity(iae.getMessage()).build();
		} catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error cogiendo un usuario", e);
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	@DELETE
	@Path("/{id}")
	public Response delete(
		@PathParam("id") int id
	) {
		try {
			this.dao.delete(id);
			
			return Response.ok(id).build();
		} catch (IllegalArgumentException iae) {
			iae.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST)
				.entity(iae.getMessage()).build();
		} catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error borrando un usuario", e);
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
	
	@PUT
	@Path("/{id}")
	public Response modify(
		@PathParam("idUsuario") int idUsuario, 
		@FormParam("login") String login, 
		@FormParam("password") String password,
		@FormParam("nombre") String nombre 
		
	) {
		try {
			return Response.ok(this.dao.modify(idUsuario, login, password, nombre)).build();
		} catch (IllegalArgumentException iae) {
			iae.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST)
				.entity(iae.getMessage()).build();
		} catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error modificando un usuario", e);
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
	
	@POST
	public Response add(
			@FormParam("login") String login, 
			@FormParam("password") String password,
			@FormParam("nombre") String nombre 
	) {
		try {
			return Response.ok(this.dao.add(login, password, nombre)).build();
		} catch (IllegalArgumentException iae) {
			iae.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST)
				.entity(iae.getMessage()).build();
		} catch (DAOException e) {
			LOG.log(Level.SEVERE, "Error a�adiendo un usuario", e);
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
}
