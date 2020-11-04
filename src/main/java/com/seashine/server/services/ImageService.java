package com.seashine.server.services;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.seashine.server.domain.Image;
import com.seashine.server.domain.Product;
import com.seashine.server.repositories.ImageRepository;
import com.seashine.server.services.exception.FileException;
import com.seashine.server.services.exception.ObjectNotFoundException;

@Service
public class ImageService {

	@Value("${img.size}")
	private Integer size;

	@Value("${img.directory}")
	private String directory;

	@Autowired
	private ImageRepository imageRepository;

	public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {
		String ext = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
		if (!"png".equals(ext) && !"jpg".equals(ext)) {
			throw new FileException("Only png and jpg images are permited");
		}

		try {
			BufferedImage img = ImageIO.read(uploadedFile.getInputStream());
			if ("png".equals(ext)) {
				img = pngToJpg(img);
			}

			return img;
		} catch (IOException e) {
			throw new FileException("Error while reading file");
		}
	}

	private BufferedImage pngToJpg(BufferedImage img) {
		BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		jpgImage.createGraphics().drawImage(img, 0, 0, Color.white, null);

		return jpgImage;
	}

	public InputStream getInputStream(BufferedImage img, String extension) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(img, extension, os);
			return new ByteArrayInputStream(os.toByteArray());
		} catch (IOException e) {
			throw new FileException("Error while reading file");
		}
	}

	public BufferedImage resize(BufferedImage sourceImg) {
		return Scalr.resize(sourceImg, Scalr.Method.ULTRA_QUALITY, sourceImg.getWidth(), sourceImg.getHeight());
	}

	public Image saveImage(MultipartFile file, int order) {
		BufferedImage jpgImage = getJpgImageFromFile(file);
		jpgImage = resize(jpgImage);

		File dir = new File(directory);
		String fileName = UUID.randomUUID().toString() + ".jpg";
		try {
			if (!dir.exists())
				dir.mkdirs();

			File uploadFile = new File(dir.getAbsolutePath() + File.separator + fileName);
			ImageIO.write(jpgImage, "jpg", uploadFile);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		Image image = new Image(null, fileName, order);
		image = imageRepository.save(image);

		return image;
	}

	public Image findById(Integer id) {
		Optional<Image> obj = imageRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Class: " + Product.class.getName()));
	}

	public byte[] getImageBytes(Image image) throws IOException {
		File dir = new File(directory);
		File file = new File(dir.getAbsolutePath() + File.separator + image.getName());

		return FileUtils.readFileToByteArray(file);
	}

	public void deleteById(Integer id) {
		Image image = findById(id);
		removeFile(image.getName());
		imageRepository.deleteById(id);
	}

	public void delete(Image image) {
		removeFile(image.getName());
	}

	private void removeFile(String fileName) {
		File dir = new File(directory);
		File imageFile = new File(dir.getAbsolutePath() + File.separator + fileName);
		imageFile.delete();
	}
}
