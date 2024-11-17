package entity.image;

public record Image(
        ImageType imageType,
        String ImageBase64,
        String mimeType
) {
}
