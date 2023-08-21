package com.book.store.bookstore.mapper;

import com.book.store.bookstore.dto.ItemDto;
import com.book.store.bookstore.entity.Item;
import com.book.store.bookstore.entity.FileCover;
import com.book.store.bookstore.repository.FileCoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Component
public class BookMapper {

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/img/";

    @Autowired
    FileCoverRepository fileCoverRepository;

    private static Optional<String> getFileExtension(String fileName) {
        final int indexOfLastDot = fileName.lastIndexOf('.');

        if (indexOfLastDot == -1) {
            return Optional.empty();
        } else {
            return Optional.of(fileName.substring(indexOfLastDot + 1));
        }
    }

    public Item map(ItemDto itemDto, MultipartFile multipartFile) throws IOException {

        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY);

        FileCover fileCover = new FileCover();
        fileCover.setPath(fileNameAndPath.toFile().getPath());
        FileCover save = fileCoverRepository.save(fileCover);

        final String fileExtension = Optional.ofNullable(multipartFile.getOriginalFilename())
                .flatMap(BookMapper::getFileExtension)
                .orElse("");

        final String targetFileName = save.getId() + "." + fileExtension;
        final Path targetPath = fileNameAndPath.resolve(targetFileName);

        File f = new File(String.valueOf(targetPath));
        multipartFile.transferTo(f);

        Item item = new Item();
        item.setTitle(itemDto.getTitle());
        item.setAuthor(itemDto.getAuthor());
        item.setDescription(itemDto.getDescription());
        item.setPrice(Double.parseDouble(itemDto.getPrice()));
        item.setQuantity(Integer.valueOf(itemDto.getQuantity()));
        item.setCoverImage(fileCover);

        return item;

    }
}
